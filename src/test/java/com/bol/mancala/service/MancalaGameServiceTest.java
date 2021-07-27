package com.bol.mancala.service;

import com.bol.mancala.model.MancalaBoard;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@DirtiesContext
class MancalaGameServiceTest {

    @Autowired
    private MancalaGameService mancalaGameService;

    @Test
    void testCreateNewGame() {
        MancalaBoard expectedMancalaBoard = new MancalaBoard().defaultPits().playerTurn(1);
//        MancalaBoard actualMancalaBoard = mancalaGameService.playGame(1);

//        assertEquals(expectedMancalaBoard.getPlayerTurn(), actualMancalaBoard.getPlayerTurn());
//        assertEquals(expectedMancalaBoard.getPits(), actualMancalaBoard.getPits());
    }
}
