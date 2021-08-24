package com.jaimedantas.fiiztaxauthenticator.model.calculator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Error {

    HttpStatus code;
    String message;

}
