package com.jaimedantas.fiiztaxauthenticator.model.calculator;

import com.jaimedantas.fiiztaxauthenticator.table.Subscricao;
import lombok.Data;

import java.util.LinkedList;

@Data
public class FiiSubscricaoList {
    LinkedList<Subscricao> fiiSubscricaoList;
}
