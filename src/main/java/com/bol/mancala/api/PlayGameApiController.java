package com.bol.mancala.api;

import com.bol.mancala.exception.MancalaBaseException;
import com.bol.mancala.model.MancalaBoard;
import com.bol.mancala.service.MancalaGameService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("${openapi.mancalaGame.base-path:}")
public class PlayGameApiController implements PlayGameApi {

    private final MancalaGameService mancalaGameService;

    public PlayGameApiController(MancalaGameService mancalaGameService) {
        this.mancalaGameService = mancalaGameService;
    }

    @Override
    public ResponseEntity<MancalaBoard> playGame(Integer pit, String gameId) throws MancalaBaseException {
        return ResponseEntity.ok(mancalaGameService.playGame(pit, gameId));
    }
}
