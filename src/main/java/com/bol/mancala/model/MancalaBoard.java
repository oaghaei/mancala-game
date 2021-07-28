package com.bol.mancala.model;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.bol.mancala.constant.MancalaConstant.*;

/**
 * MancalaBoard
 */
@Document(collection = "mancalaBoards")
public class MancalaBoard {

    @Id
    private String gameId;

    private int playerTurn;

    private List<MancalaPit> pits = null;

    private int currentPitIndex;

    public int getCurrentPitIndex() {
        return currentPitIndex;
    }

    public void setCurrentPitIndex(int currentPitIndex) {
        this.currentPitIndex = currentPitIndex;
    }

    public MancalaBoard gameId(String gameId) {
        this.gameId = gameId;
        return this;
    }

    public MancalaBoard withPlayerTurnBySelectedPitId(Integer pitId) {
        if (pitId < LEFT_MAIN_PIT_ID)
            this.setPlayerTurn(Player.PLAYER_LEFT.getPlayerId());
        else
            this.setPlayerTurn(Player.PLAYER_RIGHT.getPlayerId());
        return this;
    }

    /**
     * Game Identifier
     *
     * @return gameId
     */
    @ApiModelProperty(value = "Game Identifier")
    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public MancalaBoard playerTurn(int playerTurn) {
        this.playerTurn = playerTurn;
        return this;
    }

    /**
     * Player turn
     *
     * @return playerTurn
     */
    @ApiModelProperty(value = "Player turn")
    public int getPlayerTurn() {
        return playerTurn;
    }

    public void setPlayerTurn(int playerTurn) {
        this.playerTurn = playerTurn;
    }

    // returns the corresponding pit of particular index
    public MancalaPit getPit(Integer pitId) {
        return this.pits.get(pitId - 1);
    }

    public MancalaBoard defaultPits() {
        this.pits = Arrays.asList(
                new MancalaPit().id(1).stoneCount(DEFAULT_STONE_COUNT),
                new MancalaPit().id(2).stoneCount(DEFAULT_STONE_COUNT),
                new MancalaPit().id(3).stoneCount(DEFAULT_STONE_COUNT),
                new MancalaPit().id(4).stoneCount(DEFAULT_STONE_COUNT),
                new MancalaPit().id(5).stoneCount(DEFAULT_STONE_COUNT),
                new MancalaPit().id(6).stoneCount(DEFAULT_STONE_COUNT),
                new MancalaPit().id(7).stoneCount(DEFAULT_MAIN_PIT_STONE_COUNT),
                new MancalaPit().id(8).stoneCount(DEFAULT_STONE_COUNT),
                new MancalaPit().id(9).stoneCount(DEFAULT_STONE_COUNT),
                new MancalaPit().id(10).stoneCount(DEFAULT_STONE_COUNT),
                new MancalaPit().id(11).stoneCount(DEFAULT_STONE_COUNT),
                new MancalaPit().id(12).stoneCount(DEFAULT_STONE_COUNT),
                new MancalaPit().id(13).stoneCount(DEFAULT_STONE_COUNT),
                new MancalaPit().id(14).stoneCount(DEFAULT_MAIN_PIT_STONE_COUNT));
        return this;
    }

    public MancalaBoard pits(List<MancalaPit> pits) {
        this.pits = pits;
        return this;
    }

    public MancalaBoard addPitsItem(MancalaPit pitsItem) {
        if (this.pits == null) {
            this.pits = new ArrayList<>();
        }
        this.pits.add(pitsItem);
        return this;
    }

    /**
     * Get pits
     *
     * @return pits
     */
    @ApiModelProperty(value = "pits")
    public List<MancalaPit> getPits() {
        return pits;
    }

    public void setPits(List<MancalaPit> pits) {
        this.pits = pits;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MancalaBoard mancalaBoard = (MancalaBoard) o;
        return Objects.equals(this.gameId, mancalaBoard.gameId) &&
                Objects.equals(this.playerTurn, mancalaBoard.playerTurn) &&
                Objects.equals(this.pits, mancalaBoard.pits);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameId, playerTurn, pits);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class MancalaBoard {\n");

        sb.append("    gameId: ").append(toIndentedString(gameId)).append("\n");
        sb.append("    playerTurn: ").append(toIndentedString(playerTurn)).append("\n");
        sb.append("    pits: ").append(toIndentedString(pits)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}

