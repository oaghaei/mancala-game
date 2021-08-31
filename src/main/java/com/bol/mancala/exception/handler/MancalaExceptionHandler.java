package com.bol.mancala.exception.handler;

import com.bol.mancala.dto.ResponseErrorDto;
import com.bol.mancala.exception.MancalaBaseException;
import com.bol.mancala.model.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestControllerAdvice
public class MancalaExceptionHandler {

    @ExceptionHandler(MancalaBaseException.class)
    public final ResponseEntity<ResponseErrorDto> mancalaBaseExceptionHandler(final MancalaBaseException ex,
                                                                              final WebRequest request) {
        return new ResponseEntity<>(new ResponseErrorDto(ex.getErrorCode().getCode(),
                ex.getErrorCode().getMessage(),
                LocalDateTime.now()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseErrorDto> generalExceptionHandler(Exception ex, WebRequest request) {
        return new ResponseEntity<>(new ResponseErrorDto(ErrorCode.INTERNAL_SERVER_ERROR.getCode(),
                ErrorCode.INTERNAL_SERVER_ERROR.getMessage(),
                LocalDateTime.now()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
