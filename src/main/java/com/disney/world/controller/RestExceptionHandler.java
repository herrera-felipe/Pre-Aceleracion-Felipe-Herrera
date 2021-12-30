package com.disney.world.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.disney.world.dto.ApiErrorDTO;
import com.disney.world.exception.ParamNotFound;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Controlador para el manejo de errores
 */
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(value = { ParamNotFound.class })
    protected ResponseEntity<Object> handleParamNotFound(RuntimeException ex, WebRequest request) {

        ApiErrorDTO errorDTO = new ApiErrorDTO(HttpStatus.BAD_REQUEST, ex.getMessage(),
                Arrays.asList("Param Not Found"));
        return handleExceptionInternal(ex, errorDTO, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }


    /**
     * Captura las anotaciones @Valid
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<String> errorList = new ArrayList<>(); // Lista que contendra los errores

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errorList.add(error.getField() + ": " + error.getDefaultMessage());
        }

        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errorList.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }

        ApiErrorDTO apiError = new ApiErrorDTO(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errorList);
        return handleExceptionInternal(ex, apiError, headers, apiError.getStatus(), request);
    }


    @ExceptionHandler(value = { IllegalArgumentException.class, IllegalStateException.class })
    public ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "This should be application specific";
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

}
