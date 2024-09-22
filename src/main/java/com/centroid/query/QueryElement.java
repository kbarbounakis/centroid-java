package com.centroid.query;

import java.util.Map;

class QueryElement<K, V> implements Map.Entry<K, V> {
    protected K key;
    protected V value;

    public QueryElement(K key, V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public K getKey() {
        return key;
    }

    @Override
    public V getValue() {
        return value;
    }

    @Override
    public V setValue(V value) {
        V old = this.value;
        this.value = value;
        return old;
    }
}
