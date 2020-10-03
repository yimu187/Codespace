package com.openpayd.task.excption.handler;

import com.openpayd.task.excption.RateApiAccessException;
import com.openpayd.task.excption.RateApiException;
import com.openpayd.task.excption.RateApiInvalidJsonException;
import com.openpayd.task.excption.RatiApiClientErrorException;
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

    @ExceptionHandler(RateApiException.class)
    public final ResponseEntity<ExceptionResponse> handleGenericExcepiton(RateApiException ex, WebRequest request){
        ex.printStackTrace();
        String message = ex != null && ex.getMessage() != null ? ex.getMessage() : genericMessageText;
        ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(), message, request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.OK);
    }

    @ExceptionHandler(RateApiAccessException.class)
    public final ResponseEntity<ExceptionResponse> handleGenericExcepiton(RateApiAccessException ex, WebRequest request){
        ex.printStackTrace();
        Throwable exception = ex.getException();
        if(exception != null){
            exception.printStackTrace();
        }
        ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.OK);
    }

    @ExceptionHandler(RatiApiClientErrorException.class)
    public final ResponseEntity<ExceptionResponse> handleGenericExcepiton(RatiApiClientErrorException ex, WebRequest request){
        ex.printStackTrace();
        ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.OK);
    }

    @ExceptionHandler(RateApiInvalidJsonException.class)
    public final ResponseEntity<ExceptionResponse> handleGenericExcepiton(RateApiInvalidJsonException ex, WebRequest request){
        ex.printStackTrace();
        ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.OK);
    }
}

