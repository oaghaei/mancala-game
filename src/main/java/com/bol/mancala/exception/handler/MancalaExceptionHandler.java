package com.bol.mancala.exception.handler;

import com.bol.mancala.dto.ResponseErrorDto;
import com.bol.mancala.exception.GameIdNotFoundException;
import com.bol.mancala.model.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestControllerAdvice
public class MancalaExceptionHandler {

    @ExceptionHandler(GameIdNotFoundException.class)
    public final ResponseEntity<ResponseErrorDto> GameIdNotFoundExceptionHandler(final GameIdNotFoundException ex,
                                                                                 final WebRequest request) {
        return new ResponseEntity<>(new ResponseErrorDto(ErrorCode.GAME_ID_NOT_FOUND.getCode(),
                ErrorCode.GAME_ID_NOT_FOUND.getMessage(),
                LocalDateTime.now()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseErrorDto> generalExceptionHandler(Exception ex, WebRequest request) {
        return new ResponseEntity<>(new ResponseErrorDto(ErrorCode.INTERNAL_SERVER_ERROR.getCode(),
                ErrorCode.INTERNAL_SERVER_ERROR.getMessage(),
                LocalDateTime.now()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
