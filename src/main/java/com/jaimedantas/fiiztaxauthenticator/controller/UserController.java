package com.jaimedantas.fiiztaxauthenticator.controller;

import com.jaimedantas.fiiztaxauthenticator.model.ClientAuth;
import com.jaimedantas.fiiztaxauthenticator.model.UserInfo;
import com.jaimedantas.fiiztaxauthenticator.model.UserRequest;
import com.jaimedantas.fiiztaxauthenticator.table.User;
import com.jaimedantas.fiiztaxauthenticator.model.UserAuth;
import com.jaimedantas.fiiztaxauthenticator.repository.UserRepository;
import com.jaimedantas.fiiztaxauthenticator.service.UserService;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "https://fiiztax.com.br", maxAge = 3600)
public class UserController {

  @Autowired
  private UserService userService;

  @Autowired
  private UserRepository userRepository;

  private static final Logger logger = LoggerFactory.getLogger(UserController.class);


  @CrossOrigin(origins = "*")
  @GetMapping(path = "/signin", produces = "application/json")
  public ResponseEntity<Object> login(@RequestHeader("Authorization") String userAndPass){

    String credentials[] = userAndPass.split("Basic ");

    final byte[] decodedBytes = Base64.decodeBase64(credentials[1].getBytes());
    final String pair = new String(decodedBytes);
    final String[] userDetails = pair.split(":", 2);

    logger.info("sing in: "+userDetails[0]);

    String token = userService.signin(userDetails[0], userDetails[1]);

    User userDb = userRepository.findByUsername(userDetails[0]);


    return new ResponseEntity<>(new ClientAuth(userDb.getId(), token), HttpStatus.OK);

  }

  @CrossOrigin(origins = "*")
  @PostMapping(path = "/signup", produces = "application/json")
  public ResponseEntity<Object> registration(@RequestHeader("Authorization") String userAndPass,
                                             @RequestBody UserRequest user) {

    String credentials[] = userAndPass.split("Basic ");

    final byte[] decodedBytes = Base64.decodeBase64(credentials[1].getBytes());
    final String pair = new String(decodedBytes);
    final String[] userDetails = pair.split(":", 2);

    logger.info("sing up: "+userDetails[0]);

    User userEntity = new User();
    userEntity.setUsername(userDetails[0]);
    userEntity.setPassword(userDetails[1]);
    userEntity.setFirstName(user.getFirstName());
    userEntity.setLastName(user.getLastName());
    userEntity.setEmail(user.getEmail());

    userEntity.setRoles("admin");
/*    if (user.getRoles().equals("admin")) {
      userEntity.setRoles("admin");
    } else{
      userEntity.setRoles("client");
    }*/

    String token = userService.signup(userEntity);

    User userDb = userRepository.findByUsername(userDetails[0]);


    return new ResponseEntity<>(new ClientAuth(userDb.getId(), token), HttpStatus.OK);
  }

  @GetMapping(path = "/refresh")
  public String refresh(HttpServletRequest req) {
    return userService.refresh(req.getRemoteUser());
  }

}
