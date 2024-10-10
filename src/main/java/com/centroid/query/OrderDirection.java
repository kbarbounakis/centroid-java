package com.centroid.query;

public enum OrderDirection {
    ASC(1), DESC(-1);

    private int direction;

    OrderDirection(int direction) {
        this.direction = direction;
    }

    public int getDirection() {
        return direction;
    }
}