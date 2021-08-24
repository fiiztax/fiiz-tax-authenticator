package com.jaimedantas.fiiztaxauthenticator.service;

import com.jaimedantas.fiiztaxauthenticator.controller.UserController;
import com.jaimedantas.fiiztaxauthenticator.exception.NoSubscricaoFoundException;
import com.jaimedantas.fiiztaxauthenticator.model.calculator.*;
import com.jaimedantas.fiiztaxauthenticator.repository.SubscricoesRepository;
import com.jaimedantas.fiiztaxauthenticator.table.Subscricoes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Optional;

@Service
public class CalculatorService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private SubscricoesRepository subscricoesRepository;

    @Value("${http.fiiztax.calculator.manual.url}")
    String manualEndpoint;

    @Value("${http.fiiztax.calculator.automatic.url}")
    String automaticEndpoint;

    @Value("${http.fiiztax.calculator.subscricao.url}")
    String subscricaoEndpoint;

    @Value("${http.fiiztax.calculator.consult.url}")
    String consultEndpoint;

    @Value("${fiix-tax-api-key}")
    String apiKey;

    final static String FIIZ_TAX_API_KEY = "fiiz-tax-api-key";

    private static final Logger logger = LoggerFactory.getLogger(CalculatorService.class);

    public FiiTax calculateFiiTaxesManual(FiiData fii) {

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.set(FIIZ_TAX_API_KEY, apiKey);

        HttpEntity<FiiData> entity = new HttpEntity<FiiData>(fii,headers);

        logger.info("Enviando calculo manual para microservico");


        return restTemplate.exchange(
                manualEndpoint, HttpMethod.POST, entity, FiiTax.class).getBody();
    }

    public FiiTaxList calculateFiiTaxesAutomatic(String name, int id, boolean hasSubscricao,
                                                 LinkedList<byte[]> corretagem) throws IOException, NoSubscricaoFoundException {

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.set(FIIZ_TAX_API_KEY, apiKey);

        FiiDataPdf fiiDataPdf = new FiiDataPdf();
        fiiDataPdf.setCorretagem(corretagem);

        fiiDataPdf.setName(name);
        fiiDataPdf.setClient_id(id);
        //send all subscricao
        if(hasSubscricao) {
            Optional<Subscricoes> subscricoes = subscricoesRepository.findById((long) id);
            if (subscricoes.isPresent()) {
                fiiDataPdf.setSubscricoes(subscricoes.get().getSubscricaoList());
            } else {
                throw new NoSubscricaoFoundException();
            }
        }

        HttpEntity<FiiDataPdf> entity = new HttpEntity<FiiDataPdf>(fiiDataPdf,headers);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(automaticEndpoint);

        logger.info("Enviando calculo automatico para microservico");


        return restTemplate.exchange(
                builder.toUriString(), HttpMethod.POST, entity, FiiTaxList.class).getBody();
    }


    public FiiSubscricaoList extractSubscricao(String corretora, int client, MultipartFile extract) throws IOException {

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.set(FIIZ_TAX_API_KEY, apiKey);


        FiiSubscricaoInputPdf  fiiSubscricaoInputPdf = new FiiSubscricaoInputPdf();
        fiiSubscricaoInputPdf.setClient_id(client);
        fiiSubscricaoInputPdf.setSubscricaoPurchase(extract.getBytes());
        fiiSubscricaoInputPdf.setCorretora(corretora);


        HttpEntity<FiiSubscricaoInputPdf> entity = new HttpEntity<FiiSubscricaoInputPdf>(fiiSubscricaoInputPdf,headers);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(subscricaoEndpoint);

        logger.info("Enviando subscricao para microservico");

        return restTemplate.exchange(
                builder.toUriString(), HttpMethod.POST, entity, FiiSubscricaoList.class).getBody();
    }

}
