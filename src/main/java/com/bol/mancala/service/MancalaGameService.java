package com.bol.mancala.service;

import com.bol.mancala.exception.GameIdNotFoundException;
import com.bol.mancala.exception.InvalidSelectedPitException;
import com.bol.mancala.exception.MancalaBaseException;
import com.bol.mancala.model.MancalaBoard;
import com.bol.mancala.repository.MancalaBoardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;

@Service
public class MancalaGameService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final MancalaBoardRepository repository;
    private final GamePlayService gamePlayService;

    public MancalaGameService(MancalaBoardRepository repository, GamePlayService gamePlayService) {
        this.repository = repository;
        this.gamePlayService = gamePlayService;
    }

    public MancalaBoard playGame(Integer pitId, String gameId) throws MancalaBaseException {
        MancalaBoard mancalaBoard;
        if (gameId == null) {
            mancalaBoard = new MancalaBoard().
                    defaultPits().
                    gameId(UUID.randomUUID().toString()).
                    withPlayerTurnBySelectedPitId(pitId);
            logger.info("Game [{}] starts at [{}]", mancalaBoard.getGameId(), LocalDateTime.now());
        } else {
            Optional<MancalaBoard> mancalaBoardOpt = repository.findById(gameId);
            mancalaBoard = mancalaBoardOpt.orElseThrow(GameIdNotFoundException::new);
        }
        if (!gamePlayService.isValidPitIdToPlay(mancalaBoard, pitId)) {
            throw new InvalidSelectedPitException();
        }

        int selectedPitStoneCount = mancalaBoard.getPit(pitId).getStoneCount();
        mancalaBoard.getPit(pitId).clear();

        mancalaBoard.setCurrentPitIndex(pitId);

        IntStream.range(0, selectedPitStoneCount)
                .forEach(index -> {
                    if (index == selectedPitStoneCount - 1) {
                        gamePlayService.sow(mancalaBoard, true);
                    } else {
                        gamePlayService.sow(mancalaBoard, false);
                    }
                });

        if (gamePlayService.isGameOver(mancalaBoard)) {
            gamePlayService.addAllStonesIntoTheirMainPit(mancalaBoard);
            logger.info("Game [{}] ends at [{}]", mancalaBoard.getGameId(), LocalDateTime.now());
        }
        repository.save(mancalaBoard);
        return mancalaBoard;
    }


}
