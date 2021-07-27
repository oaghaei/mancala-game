package com.bol.mancala.dto;

import java.time.LocalDateTime;

public class ResponseErrorDto {
    private String code;
    private String message;
    private LocalDateTime dateTime;

    public ResponseErrorDto(String code, String message, LocalDateTime dateTime) {
        this.code = code;
        this.message = message;
        this.dateTime = dateTime;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
