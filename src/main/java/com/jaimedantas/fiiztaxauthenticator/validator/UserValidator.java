package com.jaimedantas.fiiztaxauthenticator.validator;

import com.jaimedantas.fiiztaxauthenticator.table.User;
import com.jaimedantas.fiiztaxauthenticator.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.jaimedantas.fiiztaxauthenticator.model.Error;

import java.util.Optional;

@Component
public class UserValidator {

    @Autowired
    private UserService userService;

    public Optional<Error> validateRegister(Object o) {
        User user = (User) o;
        Optional<Error> error = Optional.empty();
//
//        if (userService.findByUsername(user.getUsername()) != null) {
//            error = Optional.of(new Error(HttpStatus.UNPROCESSABLE_ENTITY, "Usuário já existente"));
//        }

        return error;

    }

    public Optional<Error> validateLogin(Object o) {
        User user = (User) o;
        Optional<Error> error = Optional.empty();

//        if (userService.findByUsername(user.getUsername()) == null) {
//            error = Optional.of(new Error(HttpStatus.UNPROCESSABLE_ENTITY, "Usuário não encontrado"));
//        } else {
//            User userDb = userService.findByUsername(user.getUsername());
//            if (!userDb.getPassword().equals(user.getPassword())) {
//                error = Optional.of(new Error(HttpStatus.UNPROCESSABLE_ENTITY, "Senha inválida"));
//            }
//        }

        return error;

    }
}
