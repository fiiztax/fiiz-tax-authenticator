package com.jaimedantas.fiiztaxauthenticator.model.calculator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FiiData {

    int clientId;
    String name;

    long quantityBought;
    long quantitySold;

    BigDecimal purchasePriceUnit;
    BigDecimal soldPriceUnit;

    BigDecimal totalValueBought;
    BigDecimal totalValueSold;

    String date;

}
