package com.bol.mancala.service;

import com.bol.mancala.exception.GameIdNotFoundException;
import com.bol.mancala.exception.InvalidSelectedPitException;
import com.bol.mancala.exception.MancalaBaseException;
import com.bol.mancala.model.MancalaBoard;
import com.bol.mancala.repository.MancalaBoardRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static com.bol.mancala.constant.MancalaConstant.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MancalaGameServiceTest {

    @InjectMocks
    MancalaGameService mancalaGameService;

    @Mock
    MancalaBoardRepository repositoryMock;

    @Test
    void playGameWithGameId() throws MancalaBaseException {
        MancalaBoard mancalaBoardA = new MancalaBoard().
                defaultPits().
                gameId(UUID.randomUUID().toString()).
                withPlayerTurnBySelectedPitId(1);
        when(repositoryMock.findById(any())).thenReturn(Optional.of(mancalaBoardA));
        mancalaGameService.playGame(1, "1");
        verify(repositoryMock).findById(any());
    }

    @Test
    void playGameWithoutGameId() throws MancalaBaseException {
        mancalaGameService.playGame(1, null);
        verify(repositoryMock, never()).findById(any());
    }

    @Test
    void playGameWithWrongGameId() {
        assertThrows(GameIdNotFoundException.class, () ->
                mancalaGameService.playGame(1, "123"));
    }

    @Test
    void isValidPitIdToPlay() {
        assertThrows(InvalidSelectedPitException.class, () ->
                mancalaGameService.playGame(LEFT_MAIN_PIT_ID, null));
        assertThrows(InvalidSelectedPitException.class, () ->
                mancalaGameService.playGame(RIGHT_MAIN_PIT_ID, null));
        assertThrows(InvalidSelectedPitException.class, () ->
                mancalaGameService.playGame(PIT_COUNT, null));
        assertThrows(InvalidSelectedPitException.class, () ->
                mancalaGameService.playGame(0, null));
        assertThrows(InvalidSelectedPitException.class, () ->
                mancalaGameService.playGame(PIT_COUNT, null));
    }

    @Test
    void selectClearPit() {
        MancalaBoard mancalaBoard = new MancalaBoard().
                defaultPits().
                gameId(UUID.randomUUID().toString()).
                withPlayerTurnBySelectedPitId(1);
        mancalaBoard.getPit(1).setStoneCount(0);
        when(repositoryMock.findById(mancalaBoard.getGameId())).thenReturn(Optional.of(mancalaBoard));
        assertThrows(InvalidSelectedPitException.class, () ->
                mancalaGameService.playGame(1, mancalaBoard.getGameId()));
    }

    @Test
    void playerSelectWrongSidePit() {
        MancalaBoard mancalaBoardA = new MancalaBoard().
                defaultPits().
                gameId(UUID.randomUUID().toString()).
                withPlayerTurnBySelectedPitId(1);
        when(repositoryMock.findById(mancalaBoardA.getGameId())).thenReturn(Optional.of(mancalaBoardA));
        assertThrows(InvalidSelectedPitException.class, () ->
                mancalaGameService.playGame(8, mancalaBoardA.getGameId()));

        MancalaBoard mancalaBoardB = new MancalaBoard().
                defaultPits().
                gameId(UUID.randomUUID().toString()).
                withPlayerTurnBySelectedPitId(10);
        when(repositoryMock.findById(mancalaBoardB.getGameId())).thenReturn(Optional.of(mancalaBoardB));
        assertThrows(InvalidSelectedPitException.class, () ->
                mancalaGameService.playGame(1, mancalaBoardB.getGameId()));
    }
}
