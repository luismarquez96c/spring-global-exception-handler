package com.cursosapi.common.globalexceptionhandlertest.webadvice;

import com.cursosapi.common.globalexceptionhandlertest.dto.ApiError;
import com.cursosapi.common.globalexceptionhandlertest.exception.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Log4j2
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {

        String customMessage = null;
        List<String> submessages = null;

        if(body instanceof  ProblemDetail problemDetail){
            customMessage = problemDetail.getTitle() + ": " + problemDetail.getDetail();

            submessages = problemDetail.getProperties() != null ?
                    problemDetail.getProperties().entrySet().stream()
                            .map(entry -> entry.getKey().concat(": ").concat(entry.getValue().toString()))
                            .collect(Collectors.toList())
                    :null;
        }

        return this.handleExceptionInternal(ex, customMessage, submessages, headers, statusCode, request);
    }

    private ResponseEntity<Object> handleExceptionInternal(Exception ex, String customMessage, List<String> submessages, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        log.info("Error type: {}", ex.getClass().getCanonicalName());
        log.info("Error message: {}", ex.getMessage());
        log.info("Localized error message: {}", ex.getLocalizedMessage());

        ex.printStackTrace();

        String message = StringUtils.hasText(customMessage) ? customMessage
                : ex.getLocalizedMessage();

        ApiError apiError = ApiError.builder()
                .message(message)
                .subMessages(submessages)
                .httpCode(statusCode.value())
                .build();

        return new ResponseEntity<>(apiError, new HttpHeaders(), statusCode);
    }

    @ExceptionHandler({
            AccessDeniedException.class
    })
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex, HttpServletRequest servletRequest, WebRequest webRequest){
        HttpHeaders httpHeaders = new HttpHeaders();
        return this.handleExceptionInternal(ex, "You have no permissions to access to this resource. URL: "
                + servletRequest.getMethod()  + " - " + servletRequest.getRequestURI(), null, httpHeaders, HttpStatus.FORBIDDEN, webRequest);
    }

    @ExceptionHandler({
            EntityNotFoundException.class,
            DataAccessException.class,
            Exception.class
    })
    public ResponseEntity<Object> handleException(Exception ex, HttpServletRequest servletRequest, WebRequest webRequest) {

        HttpHeaders httpHeaders = new HttpHeaders();

        if(ex instanceof EntityNotFoundException notFoundException){
            return this.handleExceptionInternal(notFoundException, null, null, httpHeaders, HttpStatus.NOT_FOUND, webRequest);
        }else if(ex instanceof DataIntegrityViolationException dataIntegrityViolationException){
            return this.handleExceptionInternal(dataIntegrityViolationException, null, null, httpHeaders, HttpStatus.NOT_FOUND, webRequest);
        }else{
            return this.handleExceptionInternal(ex, null, null, httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR, webRequest);
        }
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        String customMessage = ex.getBindingResult().getObjectName()
                .concat(" doesn't meet the required validations, see the submessages property");

        List<String> submessages = ex.getBindingResult()
                .getAllErrors().stream().map( error -> {
                    if(error instanceof FieldError fieldError){
                        return fieldError.getObjectName().concat(": ").concat(fieldError.getField())
                                .concat(" ").concat(fieldError.getDefaultMessage());
                    }

                    return "";
                } )
                .collect(Collectors.toList());

        return this.handleExceptionInternal(ex,customMessage, submessages, headers, status, request);
    }
}
