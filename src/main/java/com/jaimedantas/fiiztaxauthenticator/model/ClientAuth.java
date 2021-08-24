package com.jaimedantas.fiiztaxauthenticator.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientAuth {
    Long clientId;
    String token;
}
