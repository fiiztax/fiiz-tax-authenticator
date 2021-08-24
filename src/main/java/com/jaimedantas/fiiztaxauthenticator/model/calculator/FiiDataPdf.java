package com.jaimedantas.fiiztaxauthenticator.model.calculator;

import com.jaimedantas.fiiztaxauthenticator.table.Subscricao;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class FiiDataPdf {

    String name;
    int client_id;
    List<Subscricao> subscricoes;

    List<byte[]> corretagem;

}
