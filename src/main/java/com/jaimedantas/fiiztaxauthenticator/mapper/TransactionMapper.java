package com.jaimedantas.fiiztaxauthenticator.mapper;

import com.jaimedantas.fiiztaxauthenticator.model.calculator.FiiTax;
import com.jaimedantas.fiiztaxauthenticator.table.Transaction;

import java.util.UUID;

public class TransactionMapper {
    private TransactionMapper(){}

    public static Transaction mapTransaction(FiiTax fiiTax){

        Transaction transaction = new Transaction();
        transaction.setCorretoraFee(fiiTax.getCorretoraFee());
        transaction.setEmolumentosFee(fiiTax.getEmolumentosFee());
        transaction.setFixedTax(fiiTax.getFixedTax());
        transaction.setIRRFFee(fiiTax.getIRRFFee());
        transaction.setName(fiiTax.getName());
        transaction.setTotalProfitPercentage(fiiTax.getTotalProfitPercentage());
        transaction.setTotalProfitValue(fiiTax.getTotalProfitValue());
        transaction.setTotalTaxes(fiiTax.getTotalTaxes());
        transaction.setTotalTransactionIn(fiiTax.getTotalTransactionIn());
        transaction.setTotalTransactionOut(fiiTax.getTotalTransactionOut());
        transaction.setLiquidacaoFee(fiiTax.getLiquidacaoFee());
        transaction.setTransactionId(UUID.randomUUID().toString());
        transaction.setDate(fiiTax.getDate());

        return transaction;
    }

}
