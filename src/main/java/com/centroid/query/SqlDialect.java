package com.centroid.query;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class SqlDialect {

    protected String Space = "";
    protected String NewLine = "\n";
    protected String From = "FROM";
    protected String Where = "WHERE";
    protected String Select = "SELECT";
    protected String Distinct = "DISTINCT";
    protected String Update = "UPDATE";
    protected String Delete = "DELETE";
    protected String Insert = "INSERT INTO";
    protected String OrderBy = "ORDER BY";
    protected String GroupBy = "GROUP BY";
    protected String Values = "VALUES";
    protected String Join = "JOIN";
    protected String Set = "SET";
    protected String As = "AS";
    protected String Inner = "INNER";
    protected String Left = "LEFT";
    protected String Right = "RIGHT";
    private SqlDialectOptions options;

    public SqlDialect() {
        this.options = new SqlDialectOptions();
    }

    public SqlDialect(SqlDialectOptions options) {
        this.options = options;
    }

    public String escape(Object value) {
        if (value == null) {
            return "NULL";
        }
        if (value instanceof String) {
            return this.escapeString((String)value);
        }
        if (value instanceof Boolean) {
            return ((Boolean) value) ? "true" : "false";
        }
        if (value instanceof byte[]) {
            return this.escapeByteArray((byte[])value);
        }
        if (value instanceof Date) {
            return this.escapeDate((Date)value);
        }
        return null;
    }

    public String escapeString(String value) {
        return "'" + value.replace("'", "''") + "'";
    }

    public String escapeByteArray(byte[] value) {
        StringBuilder sb = new StringBuilder(value.length * 2);
        for(byte b: value) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public String escapeDate(Date value) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return "'" + sdf.format(value) + "'";
    }

    public String escapeDate(Date value, String timezone) {
        TimeZone tz = TimeZone.getTimeZone(timezone);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(tz);
        return "'" + sdf.format(value) + "'";
    }

    public String escapeName(String value) {
        String name = value.replaceAll("^\\$", "");
        return new ObjectNameValidator().escape(name, this.options.getNameFormat());
    }

}
