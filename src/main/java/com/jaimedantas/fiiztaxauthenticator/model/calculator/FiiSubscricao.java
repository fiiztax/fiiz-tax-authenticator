package com.jaimedantas.fiiztaxauthenticator.model.calculator;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
public class FiiSubscricao {
    String name;
    String corretora;
    Date date;
    long quantityBought;
    BigDecimal totalBought;
}
