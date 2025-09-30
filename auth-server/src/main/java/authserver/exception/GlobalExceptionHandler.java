	package authserver.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import authserver.service.UsernameNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

 // Handle UsernameNotFoundException
 @ExceptionHandler(UsernameNotFoundException.class)
 public ResponseEntity<ErrorResponse> handleUsernameNotFound(UsernameNotFoundException ex) {
     ErrorResponse error = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), ex.getMessage());
     return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
 }

 // Handle all other exceptions
 @ExceptionHandler(Exception.class)
 public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
     ErrorResponse error = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An unexpected error occurred");
     return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
 }
}
