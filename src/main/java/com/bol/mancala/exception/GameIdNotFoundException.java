package com.bol.mancala.exception;

import com.bol.mancala.model.ErrorCode;

public class GameIdNotFoundException extends MancalaBaseException{
    public GameIdNotFoundException() {
        super(ErrorCode.GAME_ID_NOT_FOUND);
    }
}
