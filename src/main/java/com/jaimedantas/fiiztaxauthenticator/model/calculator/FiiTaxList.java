package com.jaimedantas.fiiztaxauthenticator.model.calculator;

import com.jaimedantas.fiiztaxauthenticator.table.Transaction;
import lombok.Data;

import java.util.LinkedList;

@Data
public class FiiTaxList {
    LinkedList<Transaction> fiiTaxesList;
}
