package com.ppd.controller;
import com.ppd.model.ErrorDetails;
import com.ppd.model.ProdAPIResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ValidationHandler extends ResponseEntityExceptionHandler {



    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request){
        return createResponse(ex,HttpStatus.NOT_FOUND);
    }

//    @ExceptionHandler({ConstraintViolationException.class})
//    public ResponseEntity<Object> handleConstraintVCiolationException(ConstraintViolationException ex, WebRequest request){
//        ProdAPIResponse response = new ProdAPIResponse();
//        response.setStatus(-1);
//
//        return createResponse(ex,HttpStatus.BAD_REQUEST);
//    }

    private ResponseEntity<Object> createResponse(EntityNotFoundException ex, HttpStatus notFound) {
        ProdAPIResponse response = new ProdAPIResponse();
        response.setStatus(-1);
        response.setError(new ErrorDetails(100,ex.getMessage()));
        return new ResponseEntity<Object>(response, HttpStatus.NOT_FOUND);
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid
            (MethodArgumentNotValidException ex,
             HttpHeaders headers, HttpStatus status, WebRequest request) {
        ProdAPIResponse response = new ProdAPIResponse();
        List<Object> objectList = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            objectList.add(error.getDefaultMessage());
        });
        response.setStatus(-1);
        StringBuilder sb = new StringBuilder();
        for (Object s : objectList) {
            sb.append(s);
        }
        String str = sb.toString();
        response.setError(new ErrorDetails(200, str));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}