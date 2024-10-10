package com.centroid.query;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OrderByElement {
    private String expr;
    private OrderDirection direction;

    public OrderByElement(String field, OrderDirection order) {
        this.expr = field;
        this.direction = order;
    }

    @JsonProperty("$expr")
    public String getExpr() {
        return expr;
    }

    public OrderDirection getDirection() {
        return direction;
    }
}
