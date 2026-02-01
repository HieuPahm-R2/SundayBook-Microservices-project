package com.hieupahm.payload.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExceptionResponse {
    private String error;
    private String message;
    private LocalDateTime timestamp;
}
