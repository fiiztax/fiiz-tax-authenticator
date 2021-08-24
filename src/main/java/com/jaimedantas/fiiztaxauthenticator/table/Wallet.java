package com.jaimedantas.fiiztaxauthenticator.table;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "wallet")
public class Wallet {

    @Id
    private Long id;

    @ElementCollection
    @CollectionTable(
            name="transaction",
            joinColumns=@JoinColumn(name="client_id")
    )
    private List<Transaction> transactionList;

}
