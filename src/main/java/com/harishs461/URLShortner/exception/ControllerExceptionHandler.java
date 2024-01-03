package com.harishs461.URLShortner.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    public ResponseEntity<ErrorMessage> resourceNotFoundException(ResourceNotFoundException exception) {
        ErrorMessage errorMessage = new ErrorMessage(
                exception.getMessage(),
                exception.getMessage(),
                HttpStatus.NOT_FOUND.value()
        );
        return new ResponseEntity<>(errorMessage,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {MalformedUrlException.class})
    public ResponseEntity<ErrorMessage> malformedUrlException(MalformedUrlException exception) {
        ErrorMessage errorMessage = new ErrorMessage(
                exception.getMessage(),
                exception.getMessage(),
                HttpStatus.BAD_REQUEST.value()
        );
        return new ResponseEntity<>(errorMessage,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorMessage> runtimeException(Exception exception) {
        ErrorMessage errorMessage = new ErrorMessage(
                exception.getMessage(),
                exception.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
        return new ResponseEntity<>(errorMessage,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
