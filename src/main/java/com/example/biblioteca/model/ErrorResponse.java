package com.example.biblioteca.model;

import com.example.biblioteca.ErrorType;

public class ErrorResponse {
  private int statusCode;
  private String error;
  private String message;

  public ErrorResponse(ErrorType errorType, String message) {
    this.statusCode = errorType.getStatusCode();
    this.error = errorType.getMessage();
    this.message = message;
  }

  public int getStatusCode() {
    return statusCode;
  }

  public void setStatusCode(int statusCode) {
    this.statusCode = statusCode;
  }

  public String getError() {
    return error;
  }

  public void setError(String error) {
    this.error = error;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}