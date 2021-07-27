package com.bol.mancala.dto;

import java.io.Serializable;
import java.util.Objects;

public class MancalaPitDto implements Serializable {
    private Integer id;
    private Integer stoneCount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStoneCount() {
        return stoneCount;
    }

    public void setStoneCount(Integer stoneCount) {
        this.stoneCount = stoneCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MancalaPitDto that = (MancalaPitDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(stoneCount, that.stoneCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, stoneCount);
    }

    @Override
    public String toString() {
        return "MancalaPitDto{" +
                "id=" + id +
                ", stoneCount=" + stoneCount +
                '}';
    }
}
