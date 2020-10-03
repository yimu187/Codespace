package com.openpayd.task.excption.handler;

import com.openpayd.task.excption.OpedPayDAccessException;
import com.openpayd.task.excption.OpenPayDException;
import com.openpayd.task.excption.OpenPayDInvalidJsonException;
import com.openpayd.task.excption.OpenPaydClientErrorException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestController
@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

    private final String genericMessageText = "General Error Occurred. Please Consult System Administrator.";

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionResponse> handleGenericExcepiton(Exception ex, WebRequest request){
        ex.printStackTrace();
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(LocalDateTime.now(), genericMessageText, request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.OK);
    }

    @ExceptionHandler(OpenPayDException.class)
    public final ResponseEntity<ExceptionResponse> handleGenericExcepiton(OpenPayDException ex, WebRequest request){
        ex.printStackTrace();
        String message = ex != null && ex.getMessage() != null ? ex.getMessage() : genericMessageText;
        ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(), message, request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.OK);
    }

    @ExceptionHandler(OpedPayDAccessException.class)
    public final ResponseEntity<ExceptionResponse> handleGenericExcepiton(OpedPayDAccessException ex, WebRequest request){
        ex.printStackTrace();
        Throwable exception = ex.getException();
        if(exception != null){
            exception.printStackTrace();
        }
        ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.OK);
    }

    @ExceptionHandler(OpenPaydClientErrorException.class)
    public final ResponseEntity<ExceptionResponse> handleGenericExcepiton(OpenPaydClientErrorException ex, WebRequest request){
        ex.printStackTrace();
        ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.OK);
    }

    @ExceptionHandler(OpenPayDInvalidJsonException.class)
    public final ResponseEntity<ExceptionResponse> handleGenericExcepiton(OpenPayDInvalidJsonException ex, WebRequest request){
        ex.printStackTrace();
        ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.OK);
    }
}

