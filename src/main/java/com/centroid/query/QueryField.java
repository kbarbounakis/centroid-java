package com.centroid.query;

public class QueryField {
    private final String name;
    private String collection;

    public QueryField(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public String getCollection() {
        return this.collection;
    }

    public QueryField from(String collection) {
        this.collection = collection;
        return this;
    }

}
