package com.bol.mancala.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

/**
 * MancalaPit
 */
public class MancalaPit {
    private Integer id;

    private Integer stoneCount;

    public MancalaPit id(Integer id) {
        this.id = id;
        return this;
    }

    public void incrementStones() {
        this.stoneCount++;
    }

    @JsonIgnore
    public boolean isPitEmpty() {
        return this.stoneCount == 0;
    }

    public void clear() {
        this.stoneCount = 0;
    }

    public void addStones(Integer stones) {
        this.stoneCount += stones;
    }

    /**
     * Pit identifier
     *
     * @return id
     */
    @ApiModelProperty(value = "Pit identifier")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public MancalaPit stoneCount(Integer stoneCount) {
        this.stoneCount = stoneCount;
        return this;
    }

    /**
     * Stone count in a pit
     *
     * @return stoneCount
     */
    @ApiModelProperty(value = "Stone count in a pit")
    public Integer getStoneCount() {
        return stoneCount;
    }

    public void setStoneCount(Integer stoneCount) {
        this.stoneCount = stoneCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MancalaPit mancalaPit = (MancalaPit) o;
        return Objects.equals(this.id, mancalaPit.id) &&
                Objects.equals(this.stoneCount, mancalaPit.stoneCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, stoneCount);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class MancalaPit {\n");
        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    stoneCount: ").append(toIndentedString(stoneCount)).append("\n");
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

