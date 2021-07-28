package com.bol.mancala.model;

public enum Player {
    PLAYER_LEFT(1),
    PLAYER_RIGHT(2);

    private int playerId;

    public int getPlayerId() {
        return playerId;
    }

    Player(int playerId) {
        this.playerId = playerId;
    }

    @Override
    public String toString() {
        return String.valueOf(playerId);
    }
}
