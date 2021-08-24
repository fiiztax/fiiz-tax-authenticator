package com.jaimedantas.fiiztaxauthenticator.exception;

import org.springframework.http.HttpStatus;

public class NoSubscricaoFoundException extends Exception {

  private static final long serialVersionUID = -7704201287061973374L;

  private final String message;
  private final HttpStatus httpStatus;

  public NoSubscricaoFoundException(){
    this.message = "No subscricao found";
    this.httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
  }

  public NoSubscricaoFoundException(String message, HttpStatus httpStatus) {
    super(message);
    this.message = message;
    this.httpStatus = httpStatus;
  }

  @Override
  public String getMessage() {
    return message;
  }

  public HttpStatus getHttpStatus() {
    return httpStatus;
  }

}
