package com.jaimedantas.fiiztaxauthenticator.table;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "subscricoes")
public class Subscricoes {

    @Id
    private Long id;

    @ElementCollection
    @CollectionTable(
            name="subscricao",
            joinColumns=@JoinColumn(name="client_id")
    )
    private List<Subscricao> subscricaoList;

}
