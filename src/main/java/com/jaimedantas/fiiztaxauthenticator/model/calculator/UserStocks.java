package com.jaimedantas.fiiztaxauthenticator.model.calculator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserStocks {
    int userId;
    LinkedList<FiiTax> stocksList;
}
