package com.jaimedantas.fiiztaxauthenticator.table;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Date;

@Embeddable
@Getter @Setter
public class Subscricao {

  private String transactionId;

  String name;
  String corretora;
  BigDecimal totalBought;
  BigDecimal quantityBought;
  Date date;

}
