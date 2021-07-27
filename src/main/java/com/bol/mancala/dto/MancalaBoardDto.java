package com.bol.mancala.dto;

import com.bol.mancala.model.MancalaPit;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class MancalaBoardDto implements Serializable {
    private String gameId;
    private Integer playerTurn;
    private List<MancalaPitDto> pits;

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public Integer getPlayerTurn() {
        return playerTurn;
    }

    public void setPlayerTurn(Integer playerTurn) {
        this.playerTurn = playerTurn;
    }

    public List<MancalaPitDto> getPits() {
        return pits;
    }

    public void setPits(List<MancalaPitDto> pits) {
        this.pits = pits;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MancalaBoardDto that = (MancalaBoardDto) o;
        return Objects.equals(gameId, that.gameId) &&
                Objects.equals(playerTurn, that.playerTurn) &&
                Objects.equals(pits, that.pits);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameId, playerTurn, pits);
    }

    @Override
    public String toString() {
        return "MancalaBoardDto{" +
                "gameId='" + gameId + '\'' +
                ", playerTurn=" + playerTurn +
                ", pits=" + pits +
                '}';
    }
}
