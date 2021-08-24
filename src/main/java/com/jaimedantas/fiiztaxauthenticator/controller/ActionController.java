package com.jaimedantas.fiiztaxauthenticator.controller;


import com.jaimedantas.fiiztaxauthenticator.mapper.TransactionMapper;
import com.jaimedantas.fiiztaxauthenticator.model.DarfByMonth;
import com.jaimedantas.fiiztaxauthenticator.model.UserInfo;
import com.jaimedantas.fiiztaxauthenticator.model.calculator.FiiData;
import com.jaimedantas.fiiztaxauthenticator.model.calculator.FiiSubscricaoList;
import com.jaimedantas.fiiztaxauthenticator.model.calculator.FiiTax;
import com.jaimedantas.fiiztaxauthenticator.model.calculator.FiiTaxList;
import com.jaimedantas.fiiztaxauthenticator.repository.SubscricoesRepository;
import com.jaimedantas.fiiztaxauthenticator.repository.WalletRepository;
import com.jaimedantas.fiiztaxauthenticator.service.CalculatorService;
import com.jaimedantas.fiiztaxauthenticator.service.UserService;
import com.jaimedantas.fiiztaxauthenticator.table.*;
import com.jaimedantas.fiiztaxauthenticator.ultils.CalculationEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;


@RestController
@RequestMapping("/tax")
@CrossOrigin(origins = "https://fiiztax.com.br", maxAge = 3600)
public class ActionController {

    @Autowired
    private UserService userService;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private SubscricoesRepository subscricoesRepository;

    @Autowired
    CalculatorService calculatorService;

    @PostMapping(path="/fii/manual", produces = "application/json")
    public @ResponseBody FiiTax calculateFiiTaxesManual (@RequestBody FiiData fii) throws Throwable {

        FiiTax response =  calculatorService.calculateFiiTaxesManual(fii);


        List<Transaction> list;

        try{
            Optional<Wallet> wallet = walletRepository.findById( (long) fii.getClientId());
            list =  wallet.get().getTransactionList();
        } catch (Exception e){
            list = new LinkedList<>();
        }

        list.add(TransactionMapper.mapTransaction(response));

        Wallet wallet = new Wallet();
        wallet.setId((long) fii.getClientId());
        wallet.setTransactionList(list);

        walletRepository.save(wallet);

        return response;
    }

    @PostMapping(path="/fii/automatic", produces = "application/json")
    public @ResponseBody FiiTaxList calculateFiiTaxesAutomatic (@RequestParam("name") String name,
                                           @RequestParam("client_id") int id,
                                           @RequestParam("subscricao") boolean hasSubscricao,
                                           @RequestParam("corretagem0") MultipartFile corretagem0,
                                           @RequestParam(value="corretagem1", required = false) MultipartFile corretagem1,
                                           @RequestParam(value="corretagem2", required = false) MultipartFile corretagem2,
                                           @RequestParam(value="corretagem3", required = false) MultipartFile corretagem3,
                                           @RequestParam(value="corretagem4", required = false) MultipartFile corretagem4,
                                           @RequestParam(value="corretagem5", required = false) MultipartFile corretagem5,
                                           @RequestParam(value="corretagem6", required = false) MultipartFile corretagem6,
                                           @RequestParam(value="corretagem7", required = false) MultipartFile corretagem7,
                                           @RequestParam(value="corretagem8", required = false) MultipartFile corretagem8,
                                           @RequestParam(value="corretagem9", required = false) MultipartFile corretagem9,
                                           @RequestParam(value="corretagem10", required = false) MultipartFile corretagem10,
                                           @RequestParam(value="corretagem11", required = false) MultipartFile corretagem11) throws  Throwable {


        LinkedList<byte[]> multipartFiles = new LinkedList<>();
        multipartFiles.add(corretagem0.getBytes());
        if(!Objects.isNull(corretagem1)) multipartFiles.add(corretagem1.getBytes());
        if(!Objects.isNull(corretagem2)) multipartFiles.add(corretagem2.getBytes());
        if(!Objects.isNull(corretagem3)) multipartFiles.add(corretagem3.getBytes());
        if(!Objects.isNull(corretagem4)) multipartFiles.add(corretagem4.getBytes());
        if(!Objects.isNull(corretagem5)) multipartFiles.add(corretagem5.getBytes());
        if(!Objects.isNull(corretagem6)) multipartFiles.add(corretagem6.getBytes());
        if(!Objects.isNull(corretagem7)) multipartFiles.add(corretagem7.getBytes());
        if(!Objects.isNull(corretagem8)) multipartFiles.add(corretagem8.getBytes());
        if(!Objects.isNull(corretagem9)) multipartFiles.add(corretagem9.getBytes());
        if(!Objects.isNull(corretagem10)) multipartFiles.add(corretagem10.getBytes());
        if(!Objects.isNull(corretagem11)) multipartFiles.add(corretagem11.getBytes());

        FiiTaxList response = calculatorService.calculateFiiTaxesAutomatic(name, id, hasSubscricao, multipartFiles);

        List<Transaction> list;

        try{
            Optional<Wallet> wallet = walletRepository.findById( (long) id);
            list =  wallet.get().getTransactionList();
        } catch (Exception e){
            list = new LinkedList<>();
        }

        list.addAll(response.getFiiTaxesList());

        Wallet wallet = new Wallet();
        wallet.setId((long) id);
        wallet.setTransactionList(list);

        walletRepository.save(wallet);


        return response;
    }

    @GetMapping(path="/fii/wallet/{id}", produces = "application/json")
    public @ResponseBody Optional<Wallet> getFiis (@PathVariable int id) throws Throwable {

        Optional<Wallet> wallet = walletRepository.findById((long) id);

       return wallet;
    }

    @GetMapping(path="/fii/darf/{id}", produces = "application/json")
    public @ResponseBody DarfByMonth getDarf (@PathVariable int id) throws Throwable {

        Optional<Wallet> wallet = walletRepository.findById((long) id);

        DarfByMonth darfByMonth = new DarfByMonth();
        List<Transaction> transactionList = wallet.get().getTransactionList();
        List<String> month = new LinkedList<>();
        List<BigDecimal> bigDecimals = new LinkedList<>();


        transactionList.sort(Comparator.comparing(Transaction::getDate));

        transactionList.forEach( t->{
            if (!month.contains(convertToMonth(t.getDate().getTime()))){
                month.add(convertToMonth(t.getDate().getTime()));
            }
        });


        month.forEach( m -> {
            Optional<BigDecimal> reducedValue = transactionList.stream()
                    .filter(x -> m.equals(convertToMonth(x.getDate().getTime())))
                    .map(x -> x.getFixedTax())
                    .reduce(CalculationEngine::add);
            bigDecimals.add(reducedValue.get());
        });


        darfByMonth.setDarf(bigDecimals);
        darfByMonth.setMonth(month);


        return darfByMonth;
    }

    @GetMapping(path="/fii/darf_ano/{id}", produces = "application/json")
    public @ResponseBody DarfByMonth getDarfAno (@PathVariable int id) throws Throwable {

        Optional<Wallet> wallet = walletRepository.findById((long) id);

        DarfByMonth darfByMonth = new DarfByMonth();
        List<Transaction> transactionList = wallet.get().getTransactionList();
        List<String> month = new LinkedList<>();
        List<BigDecimal> bigDecimals = new LinkedList<>();


        transactionList.sort(Comparator.comparing(Transaction::getDate));

        transactionList.forEach( t->{
            if (!month.contains(convertToYear(t.getDate().getTime()))){
                month.add(convertToYear(t.getDate().getTime()));
            }
        });


        month.forEach( m -> {
            Optional<BigDecimal> reducedValue = transactionList.stream()
                    .filter(x -> m.equals(convertToYear(x.getDate().getTime())))
                    .map(x -> x.getFixedTax())
                    .reduce(CalculationEngine::add);
            bigDecimals.add(reducedValue.get());
        });


        darfByMonth.setDarf(bigDecimals);
        darfByMonth.setMonth(month);


        return darfByMonth;
    }

    @GetMapping(path="/fii/subscricao/{id}", produces = "application/json")
    public @ResponseBody Optional<Subscricoes> getFiisSubscricao (@PathVariable int id) throws Throwable {

        Optional<Subscricoes> subscricoes = subscricoesRepository.findById((long) id);

        return subscricoes;
    }

    @DeleteMapping(path="/fii/wallet/{id}/{transactionId}", produces = "application/json")
    public @ResponseBody String deleteFiis (@PathVariable int id, @PathVariable String transactionId) throws Throwable {

        Optional<Wallet> wallet = walletRepository.findById((long) id);
        List<Transaction> list =  wallet.get().getTransactionList();
        list.removeIf(e -> e.getTransactionId().equals(transactionId));
        walletRepository.deleteById((long) id);

        Wallet newWallet = new Wallet();
        newWallet.setId((long) id);
        newWallet.setTransactionList(list);
        walletRepository.save(newWallet);


        return "OK";
    }

    @DeleteMapping(path="/fii/subscricao/{id}/{transactionId}", produces = "application/json")
    public @ResponseBody String deleteSubscricao (@PathVariable int id, @PathVariable String transactionId) throws Throwable {

        Optional<Subscricoes> subscricoes = subscricoesRepository.findById((long) id);
        List<Subscricao> list =  subscricoes.get().getSubscricaoList();
        list.removeIf(e -> e.getTransactionId().equals(transactionId));
        subscricoesRepository.deleteById((long) id);

        Subscricoes newSubscricoes = new Subscricoes();
        newSubscricoes.setId((long) id);
        newSubscricoes.setSubscricaoList(list);
        subscricoesRepository.save(newSubscricoes);


        return "OK";
    }

    @GetMapping(path = "/userinfo")
    public ResponseEntity<UserInfo> whoami(HttpServletRequest req) {

        User user = userService.whoami(req);

        UserInfo userInfo = new UserInfo();
        userInfo.setEmail(user.getEmail());
        userInfo.setFirstName(user.getFirstName());
        userInfo.setLastName(user.getLastName());
        userInfo.setUsername(user.getUsername());

        return new ResponseEntity<>(userInfo, HttpStatus.OK);
    }

    @PostMapping(path="/fii/subscricao", produces = "application/json")
    public @ResponseBody FiiSubscricaoList calculateFiiTaxesAutomaticNoName (@RequestParam("corrretora") String corretora,
                                                        @RequestParam("client_id") int id,
                                                        @RequestParam("extract") MultipartFile extract) throws Throwable {

        FiiSubscricaoList response = calculatorService.extractSubscricao(corretora, id, extract);

        List<Subscricao> list;

        try{
            Optional<Subscricoes> subscricoes = subscricoesRepository.findById( (long) id);
            list =  subscricoes.get().getSubscricaoList();
        } catch (Exception e){
            list = new LinkedList<>();
        }

        list.addAll(response.getFiiSubscricaoList());

        Subscricoes subscricoes = new Subscricoes();
        subscricoes.setId((long) id);
        subscricoes.setSubscricaoList(list);

        subscricoesRepository.save(subscricoes);


        return response;

    }

    /**
     * This function returns the MM/YYYY for a given time
     * @param time from date
     * @return date in MM/YYYY
     */
    private static String convertToMonth(long time){
        Date date = new Date(time);
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        String mes;
        switch (month){
            case 1:
                mes = "Jan";
                break;
            case 2:
                mes = "Fev";
                break;
            case 3:
                mes = "Mar";
                break;
            case 4:
                mes = "Abr";
                break;
            case 5:
                mes = "Mai";
                break;
            case 6:
                mes = "Jun";
                break;
            case 7:
                mes = "Jul";
                break;
            case 8:
                mes = "Ago";
                break;
            case 9:
                mes = "Set";
                break;
            case 10:
                mes = "Out";
                break;
            case 11:
                mes = "Nov";
                break;
            case 12:
                mes = "Dez";
                break;
            default:
                mes = String.valueOf(month);
        }
        return mes + "/" + year;
    }

    /**
     * This function returns the YYYY for a given time
     * @param time from date
     * @return date in YYYY
     */
    private static String convertToYear(long time){
        Date date = new Date(time);
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        return String.valueOf(calendar.get(Calendar.YEAR));
    }

}
