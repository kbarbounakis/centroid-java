package com.centroid.query;

import java.util.LinkedHashMap;

public class QueryElemenCollection<K, V> extends LinkedHashMap<K, V> {
    public QueryElemenCollection() {
        super();
    }

    public QueryElemenCollection<K, V> add(K key, V value) {
        this.put(key, value);
        return this;
    }

}
