package uz.tuit.press.controller;

import uz.tuit.press.exception.AppForbiddenException;
import uz.tuit.press.exception.GlobalException;
import uz.tuit.press.exception.TokenNotValidException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler({GlobalException.class})
    public ResponseEntity<?> handleBadRequestException(RuntimeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler({AppForbiddenException.class})
    public ResponseEntity<?> handleForbiddenException(RuntimeException e){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    }

    @ExceptionHandler({TokenNotValidException.class})
    public ResponseEntity<?> handleNotValidException(TokenNotValidException e){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

}