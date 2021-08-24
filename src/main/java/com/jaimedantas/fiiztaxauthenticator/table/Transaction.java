package com.jaimedantas.fiiztaxauthenticator.table;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Embeddable
@Getter @Setter
public class Transaction {

  private String transactionId;

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
