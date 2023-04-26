package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.CONFLICT, reason="Movie Id Not Present, handled by Custom Exception")
public class MovieIdNotPresentException extends Exception{

}
