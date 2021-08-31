package com.bol.mancala.exception;

import com.bol.mancala.model.ErrorCode;

public class InvalidSelectedPitException extends MancalaBaseException{
    public InvalidSelectedPitException() {
        super(ErrorCode.INVALID_SELECTED_PIT);
    }
}