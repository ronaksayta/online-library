package com.ronakcode.onlinelibrary.exceptions;

import com.ronakcode.onlinelibrary.dto.MessageData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler({UserNotFoundException.class, BookNotFoundException.class})
    public ResponseEntity<MessageData> handlerNotFound(Exception e) {
        return new ResponseEntity<>(new MessageData(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<MessageData> authenticationFailure(Exception e) {
        return new ResponseEntity<>(new MessageData("Invalid credentials"), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({DuplicateEmailException.class})
    public ResponseEntity<MessageData> duplicateEmailException(Exception e) {
        return new ResponseEntity<>(new MessageData("Email already in use"), HttpStatus.BAD_REQUEST);
    }
}
