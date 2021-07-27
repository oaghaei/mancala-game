package com.bol.mancala.model;

public enum Player {
    PlayerA(1),
    PlayerB(2);

    private Integer playerId;

    public Integer getPlayerId() {
        return playerId;
    }

    Player(Integer playerId) {
        this.playerId = playerId;
    }

    @Override
    public String toString() {
        return String.valueOf(playerId);
    }
}
