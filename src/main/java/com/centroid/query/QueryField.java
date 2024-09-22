package com.centroid.query;

public class QueryField extends QueryElement<String,Object> {
    
    public static String trimFieldReference(String value) {
        if (value.startsWith("$")) {
            return value.substring(1);
        }
        return value;
    }

    public QueryField(String name) {
        super(name, 1);
    }

    public QueryField() {
        super(null, 0);
    }

    public String getName() {
        if (this.getKey() == null) {
            return null;
        }
        String key = this.getKey();
        String[] array = key.split("\\.");
        return array[array.length - 1];
    }

    public String getCollection() {
        if (this.getKey() == null) {
            return null;
        }
        String key = this.getKey();
        String[] array = key.split(".");
        if (array.length == 1) {
            return null;
        }
        return array[array.length - 1];
    }

    public QueryField from(String collection) {
        this.key = collection + "." + this.key;
        return this;
    }

}
