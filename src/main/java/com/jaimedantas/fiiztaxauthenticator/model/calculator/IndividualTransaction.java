package com.jaimedantas.fiiztaxauthenticator.model.calculator;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
public class IndividualTransaction {

    String name;
    BigDecimal total;
    String transactionType;

}
