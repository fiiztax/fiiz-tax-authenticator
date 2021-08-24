package com.jaimedantas.fiiztaxauthenticator.model.calculator;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class FiiSubscricaoInputPdf {
    String corretora;
    int client_id;
    byte[] subscricaoPurchase;
}
