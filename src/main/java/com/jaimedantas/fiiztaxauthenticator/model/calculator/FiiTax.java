package com.jaimedantas.fiiztaxauthenticator.model.calculator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FiiTax {
    String name;
    BigDecimal totalTransactionIn;
    BigDecimal totalTransactionOut;
    BigDecimal totalTaxes;
    BigDecimal totalProfitPercentage;
    BigDecimal totalProfitValue;
    BigDecimal liquidacaoFee;
    BigDecimal emolumentosFee;
    BigDecimal IRRFFee;
    BigDecimal fixedTax;
    BigDecimal corretoraFee;
    Date date;

}
