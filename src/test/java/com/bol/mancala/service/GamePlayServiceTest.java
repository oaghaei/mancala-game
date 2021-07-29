package com.bol.mancala.service;

import com.bol.mancala.model.MancalaBoard;
import com.bol.mancala.model.MancalaPit;
import com.bol.mancala.model.Player;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.IntStream;

import static com.bol.mancala.constant.MancalaBoardTestCase.*;
import static com.bol.mancala.constant.MancalaConstant.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GamePlayServiceTest {

    private GamePlayService gamePlayService = new GamePlayService();
    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void isValidPitIdToPlayTest() {
        MancalaBoard mancalaBoard = new MancalaBoard().
                defaultPits().
                gameId(UUID.randomUUID().toString()).
                withPlayerTurnBySelectedPitId(Player.PLAYER_LEFT.getPlayerId());
        assertFalse(gamePlayService.isValidPitIdToPlay(mancalaBoard, LEFT_MAIN_PIT_ID));
        assertFalse(gamePlayService.isValidPitIdToPlay(mancalaBoard, RIGHT_MAIN_PIT_ID));
        assertFalse(gamePlayService.isValidPitIdToPlay(mancalaBoard, 0));
        assertTrue(gamePlayService.isValidPitIdToPlay(mancalaBoard, 5));
        assertFalse(gamePlayService.isValidPitIdToPlay(mancalaBoard, 10));
        mancalaBoard.setPlayerTurn(Player.PLAYER_RIGHT.getPlayerId());
        assertFalse(gamePlayService.isValidPitIdToPlay(mancalaBoard, 5));
        assertTrue(gamePlayService.isValidPitIdToPlay(mancalaBoard, 10));
    }

    @Test
    void isGameOverTest() {
        MancalaBoard mancalaBoard = new MancalaBoard().
                defaultPits().
                gameId(UUID.randomUUID().toString()).
                withPlayerTurnBySelectedPitId(Player.PLAYER_LEFT.getPlayerId());
        assertFalse(gamePlayService.isGameOver(mancalaBoard));

        mancalaBoard.pits(Arrays.asList(
                new MancalaPit().id(1).stoneCount(0),
                new MancalaPit().id(2).stoneCount(0),
                new MancalaPit().id(3).stoneCount(0),
                new MancalaPit().id(4).stoneCount(0),
                new MancalaPit().id(5).stoneCount(0),
                new MancalaPit().id(6).stoneCount(0),
                new MancalaPit().id(7).stoneCount(DEFAULT_MAIN_PIT_STONE_COUNT),
                new MancalaPit().id(8).stoneCount(DEFAULT_STONE_COUNT),
                new MancalaPit().id(9).stoneCount(DEFAULT_STONE_COUNT),
                new MancalaPit().id(10).stoneCount(DEFAULT_STONE_COUNT),
                new MancalaPit().id(11).stoneCount(DEFAULT_STONE_COUNT),
                new MancalaPit().id(12).stoneCount(DEFAULT_STONE_COUNT),
                new MancalaPit().id(13).stoneCount(DEFAULT_STONE_COUNT),
                new MancalaPit().id(14).stoneCount(DEFAULT_MAIN_PIT_STONE_COUNT)));
        assertTrue(gamePlayService.isGameOver(mancalaBoard));

        mancalaBoard.pits(Arrays.asList(
                new MancalaPit().id(1).stoneCount(DEFAULT_STONE_COUNT),
                new MancalaPit().id(2).stoneCount(DEFAULT_STONE_COUNT),
                new MancalaPit().id(3).stoneCount(DEFAULT_STONE_COUNT),
                new MancalaPit().id(4).stoneCount(DEFAULT_STONE_COUNT),
                new MancalaPit().id(5).stoneCount(DEFAULT_STONE_COUNT),
                new MancalaPit().id(6).stoneCount(DEFAULT_STONE_COUNT),
                new MancalaPit().id(7).stoneCount(DEFAULT_MAIN_PIT_STONE_COUNT),
                new MancalaPit().id(8).stoneCount(0),
                new MancalaPit().id(9).stoneCount(0),
                new MancalaPit().id(10).stoneCount(0),
                new MancalaPit().id(11).stoneCount(0),
                new MancalaPit().id(12).stoneCount(0),
                new MancalaPit().id(13).stoneCount(0),
                new MancalaPit().id(14).stoneCount(DEFAULT_MAIN_PIT_STONE_COUNT)));
        assertTrue(gamePlayService.isGameOver(mancalaBoard));
    }

    @Test
    void addAllStonesIntoTheirMainPitTest() {
        MancalaBoard mancalaBoard = new MancalaBoard().
                defaultPits().
                gameId(UUID.randomUUID().toString()).
                withPlayerTurnBySelectedPitId(Player.PLAYER_LEFT.getPlayerId());
        gamePlayService.addAllStonesIntoTheirMainPit(mancalaBoard);
        assertEquals(36, mancalaBoard.getPit(LEFT_MAIN_PIT_ID).getStoneCount());
        assertEquals(36, mancalaBoard.getPit(RIGHT_MAIN_PIT_ID).getStoneCount());
    }

    @Test
    void sowTest() throws IOException {
        MancalaBoard model = objectMapper.readValue(readJsonCases(TEST_CASE_SOW_JSON_1), MancalaBoard.class);
        MancalaBoard expectedModel = objectMapper.readValue(readJsonCases(TEST_CASE_SOW_EXPECTED_RESULT_JSON_1), MancalaBoard.class);
        gamePlayService.sow(model, true);

        assertEquals(expectedModel, model);
    }

    @Test
    void sow2Test() throws IOException {
        MancalaBoard model = objectMapper.readValue(readJsonCases(TEST_CASE_SOW_JSON_2), MancalaBoard.class);
        MancalaBoard expectedModel = objectMapper.readValue(readJsonCases(TEST_CASE_SOW_EXPECTED_RESULT_JSON_2), MancalaBoard.class);
        int selectedPitStoneCount = model.getPit(9).getStoneCount();
        model.getPit(9).clear();
        IntStream.range(0, selectedPitStoneCount)
                .forEach(index -> {
                    if (index == selectedPitStoneCount - 1) {
                        gamePlayService.sow(model, true);
                    } else {
                        gamePlayService.sow(model, false);
                    }
                });

        assertEquals(expectedModel, model);
    }


    private String readJsonCases(String filename) throws IOException {
        return StreamUtils.copyToString(new ClassPathResource(filename).getInputStream(), Charset.defaultCharset());
    }
}
