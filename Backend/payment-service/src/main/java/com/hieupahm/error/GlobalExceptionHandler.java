package com.hieupahm.error;

import java.time.LocalDateTime;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.hieupahm.payload.res.ExceptionResponse;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ExceptionResponse> handleUserException(UserException ex, WebRequest req) {
        log.warn("UserException occurred: {}", ex.getMessage());

        ExceptionResponse response = new ExceptionResponse(
                ex.getMessage(),
                req.getDescription(false),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PaymentException.class)
    public ResponseEntity<ExceptionResponse> handlePaymentException(PaymentException ex, WebRequest req) {
        log.warn("PaymentException occurred: {}", ex.getMessage());

        ExceptionResponse response = new ExceptionResponse(
                ex.getMessage(),
                req.getDescription(false),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleGlobalException(Exception ex, WebRequest req) {
        log.error("Unhandled Exception occurred: ", ex);

        ExceptionResponse response = new ExceptionResponse(
                "Đã có lỗi hệ thống xảy ra. Vui lòng thử lại sau!",
                req.getDescription(false),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}