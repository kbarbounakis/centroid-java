package com.centroid.query;

public class SqlDialectOptions {
    private String nameFormat = "$1";
    public String getNameFormat() {
        return nameFormat;
    }

    public void setNameFormat(String nameFormat) {
        this.nameFormat = nameFormat;
    }

    private boolean forceAlias = false;

    public boolean isForceAlias() {
        return forceAlias;
    }

    public void setForceAlias(boolean forceAlias) {
        this.forceAlias = forceAlias;
    }

    public SqlDialectOptions() {
    }

    public SqlDialectOptions(String nameFormat, boolean forceAlias) {
        this.nameFormat = nameFormat;
        this.forceAlias = forceAlias;
    }
}
