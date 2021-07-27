package com.bol.mancala.model;

public enum ErrorCode {
    INTERNAL_SERVER_ERROR("001", "internal server error"),
    GAME_ID_NOT_FOUND("002", "game not found"),
    INVALID_SELECTED_PIT("003", "selected pit is invalid");

    private final String code;
    private final String message;

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
