package com.jaimedantas.fiiztaxauthenticator.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

    String email;

    String firstName;

    String lastName;

}
