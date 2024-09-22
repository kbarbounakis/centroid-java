package com.centroid.query;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;
import java.util.LinkedHashMap;

public class QueryExpression {

    @JsonProperty("$select")
    protected LinkedHashMap<String,Object> selectElement = new LinkedHashMap<String,Object>();

    public QueryExpression() {

    }

    public LinkedHashMap<String,Object> getSelectElement() {
        return this.selectElement;
    }

    public QueryExpression select(String... fields) {
        Arrays.stream(fields).forEach(field -> {
            this.selectElement.put(field, 1);
        });
        return this;
    }

}
