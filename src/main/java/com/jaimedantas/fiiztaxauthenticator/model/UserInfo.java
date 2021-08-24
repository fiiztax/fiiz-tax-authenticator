package com.jaimedantas.fiiztaxauthenticator.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserInfo {
    String firstName;
    String lastName;
    String email;
    String username;
}
