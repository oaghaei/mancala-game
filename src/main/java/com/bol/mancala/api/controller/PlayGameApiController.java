package com.bol.mancala.api.controller;

import com.bol.mancala.api.PlayGameApi;
import com.bol.mancala.dto.MancalaBoardDto;
import com.bol.mancala.exception.MancalaBaseException;
import com.bol.mancala.model.MancalaBoard;
import com.bol.mancala.service.MancalaGameService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("${openapi.mancalaGame.base-path:}")
public class PlayGameApiController implements PlayGameApi {

    private final MancalaGameService mancalaGameService;
    private final ModelMapper modelMapper;

    public PlayGameApiController(MancalaGameService mancalaGameService, ModelMapper modelMapper) {
        this.mancalaGameService = mancalaGameService;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseEntity<MancalaBoardDto> playGame(Integer pit, String gameId) throws MancalaBaseException {
        MancalaBoard mancalaBoard = mancalaGameService.playGame(pit, gameId);
        return ResponseEntity.ok(modelMapper.map(mancalaBoard, MancalaBoardDto.class));
    }
}
