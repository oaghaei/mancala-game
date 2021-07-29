package com.bol.mancala.service;

import com.bol.mancala.model.MancalaBoard;
import com.bol.mancala.model.MancalaPit;
import com.bol.mancala.model.Player;
import org.springframework.stereotype.Service;

import java.util.stream.IntStream;

import static com.bol.mancala.constant.MancalaConstant.*;

@Service
public class GamePlayService {

    public void sow(MancalaBoard mancalaBoard, boolean lastStone) {
        int currentPitId = mancalaBoard.getCurrentPitIndex() % PIT_COUNT + 1;
        int playerId = mancalaBoard.getPlayerTurn();

        if ((currentPitId == RIGHT_MAIN_PIT_ID && playerId == Player.PLAYER_LEFT.getPlayerId()) ||
                (currentPitId == LEFT_MAIN_PIT_ID && playerId == Player.PLAYER_RIGHT.getPlayerId()))
            currentPitId = currentPitId % PIT_COUNT + 1;

        mancalaBoard.setCurrentPitIndex(currentPitId);

        MancalaPit currentPit = mancalaBoard.getPit(currentPitId);

        if (!lastStone || currentPitId == RIGHT_MAIN_PIT_ID || currentPitId == LEFT_MAIN_PIT_ID) {
            currentPit.incrementStones();
            return;
        }

        if (playerId == Player.PLAYER_LEFT.getPlayerId()) {
            mancalaBoard.setPlayerTurn(Player.PLAYER_RIGHT.getPlayerId());
        } else {
            mancalaBoard.setPlayerTurn(Player.PLAYER_LEFT.getPlayerId());
        }
        // It's the last stone and we need to capturing stones if it is possible!
        if (currentPit.isPitEmpty() && (
                (playerId == Player.PLAYER_LEFT.getPlayerId() && currentPit.getId() < LEFT_MAIN_PIT_ID) ||
                        (playerId == Player.PLAYER_RIGHT.getPlayerId() && currentPit.getId() > LEFT_MAIN_PIT_ID))) {
            // check the opposite player's pit status
            MancalaPit oppositePit = mancalaBoard.getPit(PIT_COUNT - currentPitId);
            Integer oppositeStones = oppositePit.getStoneCount();
            oppositePit.clear();
            Integer mainPitId = currentPitId < LEFT_MAIN_PIT_ID ? LEFT_MAIN_PIT_ID : RIGHT_MAIN_PIT_ID;
            MancalaPit mainPit = mancalaBoard.getPit(mainPitId);
            mainPit.addStones(oppositeStones + 1);
            return;
        }
        currentPit.incrementStones();
    }

    public boolean isValidPitIdToPlay(MancalaBoard mancalaBoard, Integer pitId) {
        if (pitId == RIGHT_MAIN_PIT_ID || pitId == LEFT_MAIN_PIT_ID || pitId > PIT_COUNT || pitId <= 0) {
            return false;
        }
        if (mancalaBoard.getPlayerTurn() == Player.PLAYER_LEFT.getPlayerId() && pitId > LEFT_MAIN_PIT_ID ||
                mancalaBoard.getPlayerTurn() == Player.PLAYER_RIGHT.getPlayerId() && pitId < LEFT_MAIN_PIT_ID) {
            return false;
        }
        if (mancalaBoard.getPit(pitId).getStoneCount() == 0) {
            return false;
        }
        return true;
    }

    public boolean isGameOver(MancalaBoard mancalaBoard) {
        return IntStream.range(1, LEFT_MAIN_PIT_ID).allMatch(index -> mancalaBoard.getPit(index).getStoneCount() == 0) ||
                IntStream.range(LEFT_MAIN_PIT_ID + 1, RIGHT_MAIN_PIT_ID).allMatch(index -> mancalaBoard.getPit(index).getStoneCount() == 0);
    }

    public void addAllStonesIntoTheirMainPit(MancalaBoard mancalaBoard) {
        int rightPitsStoneCount = IntStream.range(LEFT_MAIN_PIT_ID + 1, RIGHT_MAIN_PIT_ID).map(index -> mancalaBoard.getPit(index).getStoneCount()).sum();
        if (rightPitsStoneCount != 0) {
            mancalaBoard.getPit(RIGHT_MAIN_PIT_ID).addStones(rightPitsStoneCount);
            IntStream.range(LEFT_MAIN_PIT_ID + 1, RIGHT_MAIN_PIT_ID).forEach(index -> mancalaBoard.getPit(index).clear());
        }
        int leftPitsStoneCount = IntStream.range(1, LEFT_MAIN_PIT_ID).map(index -> mancalaBoard.getPit(index).getStoneCount()).sum();
        if (leftPitsStoneCount != 0) {
            mancalaBoard.getPit(LEFT_MAIN_PIT_ID).addStones(leftPitsStoneCount);
            IntStream.range(1, LEFT_MAIN_PIT_ID).forEach(index -> mancalaBoard.getPit(index).clear());
        }
    }
}
