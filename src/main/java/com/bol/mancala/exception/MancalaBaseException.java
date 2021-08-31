package com.bol.mancala.exception;

import com.bol.mancala.model.ErrorCode;

public class MancalaBaseException extends Exception {
    private final ErrorCode errorCode;

    public MancalaBaseException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
