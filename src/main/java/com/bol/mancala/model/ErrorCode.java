package com.bol.mancala.model;

public enum ErrorCode {
    GAME_ID_NOT_FOUND("400", "game not found"),
    INTERNAL_SERVER_ERROR("500", "internal server error");

    private String code;
    private String message;

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
