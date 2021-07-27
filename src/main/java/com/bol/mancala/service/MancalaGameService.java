package com.bol.mancala.service;

import com.bol.mancala.constant.MancalaConstant;
import com.bol.mancala.exception.GameIdNotFoundException;
import com.bol.mancala.exception.MancalaBaseException;
import com.bol.mancala.model.MancalaBoard;
import com.bol.mancala.model.MancalaPit;
import com.bol.mancala.model.Player;
import com.bol.mancala.repository.MancalaBoardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;

import static com.bol.mancala.constant.MancalaConstant.LEFT_MAIN_PIT_ID;
import static com.bol.mancala.constant.MancalaConstant.RIGHT_MAIN_PIT_ID;

@Service
public class MancalaGameService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final MancalaBoardRepository repository;

    public MancalaGameService(MancalaBoardRepository repository) {
        this.repository = repository;
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
        if (!isValid(mancalaBoard, pitId)) {
            return mancalaBoard;
        }

        int selectedPitStoneCount = mancalaBoard.getPit(pitId).getStoneCount();
        mancalaBoard.getPit(pitId).clear();

        mancalaBoard.setCurrentPitIndex(pitId);

        IntStream.range(0, selectedPitStoneCount - 1)
                .forEach(index -> sow(mancalaBoard, false));

        sow(mancalaBoard, true);

        int lastPitId = mancalaBoard.getCurrentPitIndex();

        // If the player's last stone lands in his own big pit, he gets another turn. Otherwise we switch the turn
        if (lastPitId != RIGHT_MAIN_PIT_ID && lastPitId != LEFT_MAIN_PIT_ID) {
            if (mancalaBoard.getPlayerTurn().equals(Player.PlayerA.getPlayerId())) {
                mancalaBoard.setPlayerTurn(Player.PlayerB.getPlayerId());
            } else {
                mancalaBoard.setPlayerTurn(Player.PlayerA.getPlayerId());
            }
        }

        if (IntStream.range(1, LEFT_MAIN_PIT_ID).allMatch(index -> mancalaBoard.getPit(index).getStoneCount() == 0)) {
            mancalaBoard.getPit(RIGHT_MAIN_PIT_ID).addStones(
                    IntStream.range(LEFT_MAIN_PIT_ID + 1, RIGHT_MAIN_PIT_ID).map(index -> mancalaBoard.getPit(index).getStoneCount()).sum());
            IntStream.range(LEFT_MAIN_PIT_ID + 1, RIGHT_MAIN_PIT_ID).forEach(index -> mancalaBoard.getPit(index).setStoneCount(0));
        } else if (IntStream.range(LEFT_MAIN_PIT_ID + 1, RIGHT_MAIN_PIT_ID).allMatch(index -> mancalaBoard.getPit(index).getStoneCount() == 0)) {
            mancalaBoard.getPit(LEFT_MAIN_PIT_ID).addStones(
                    IntStream.range(1, LEFT_MAIN_PIT_ID).map(index -> mancalaBoard.getPit(index).getStoneCount()).sum());
            IntStream.range(1, LEFT_MAIN_PIT_ID).forEach(index -> mancalaBoard.getPit(index).setStoneCount(0));
        }
        repository.save(mancalaBoard);
        return mancalaBoard;
    }

    // sows the stones on to the right
    private void sow(MancalaBoard mancalaBoard, boolean lastStone) {
        int nextPitId = mancalaBoard.getCurrentPitIndex() % MancalaConstant.PIT_COUNT + 1;
        Integer playerId = mancalaBoard.getPlayerTurn();

        if ((nextPitId == RIGHT_MAIN_PIT_ID && playerId.equals(Player.PlayerA.getPlayerId())) ||
                (nextPitId == LEFT_MAIN_PIT_ID && playerId.equals(Player.PlayerB.getPlayerId())))
            nextPitId = nextPitId % MancalaConstant.PIT_COUNT + 1;

        mancalaBoard.setCurrentPitIndex(nextPitId);

        MancalaPit targetPit = mancalaBoard.getPit(nextPitId);
        if (!lastStone || nextPitId == RIGHT_MAIN_PIT_ID || nextPitId == LEFT_MAIN_PIT_ID) {
            targetPit.incrementStones();
            return;
        }

        // It's the last stone and we need to check the opposite player's pit status
        MancalaPit oppositePit = mancalaBoard.getPit(MancalaConstant.PIT_COUNT - nextPitId);

        // Capturing Stones
        if (targetPit.isPitEmpty() && (
                (mancalaBoard.getPlayerTurn().equals(Player.PlayerA.getPlayerId()) && targetPit.getId() < LEFT_MAIN_PIT_ID) ||
                        (mancalaBoard.getPlayerTurn().equals(Player.PlayerB.getPlayerId()) && targetPit.getId() > LEFT_MAIN_PIT_ID))) {
            Integer oppositeStones = oppositePit.getStoneCount();
            oppositePit.clear();
            Integer mainPitId = nextPitId < LEFT_MAIN_PIT_ID ? LEFT_MAIN_PIT_ID : RIGHT_MAIN_PIT_ID;
            MancalaPit mainPit = mancalaBoard.getPit(mainPitId);
            mainPit.addStones(oppositeStones + 1);
            return;
        }
        targetPit.incrementStones();
    }

    private boolean isValid(MancalaBoard mancalaBoard, Integer pitId) {
        if (pitId == RIGHT_MAIN_PIT_ID || pitId == LEFT_MAIN_PIT_ID) {
            return false;
        }
        if (mancalaBoard.getPlayerTurn().equals(Player.PlayerA.getPlayerId()) && pitId > LEFT_MAIN_PIT_ID ||
                mancalaBoard.getPlayerTurn().equals(Player.PlayerB.getPlayerId()) && pitId < LEFT_MAIN_PIT_ID) {
            return false;
        }
        if (mancalaBoard.getPit(pitId).getStoneCount() == 0) {
            return false;
        }
        return true;
    }
}
