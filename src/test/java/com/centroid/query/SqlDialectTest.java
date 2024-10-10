package com.centroid.query;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;



public class SqlDialectTest {

    @Test
    public void testEscapeDateWithTimezone() {
        SqlDialect sqlDialect = new SqlDialect();
        Date date = new Date(0); // Epoch time
        String expected = "'1970-01-01 01:00:00'"; // Amsterdam is UTC+1

        String result = sqlDialect.escapeDate(date, "Europe/Amsterdam");
        assertEquals(expected, result);
    }

    @Test
    public void testEscapeDateWithDifferentTimezone() {
        SqlDialect sqlDialect = new SqlDialect();
        Date date = new Date(0); // Epoch time
        // Manually set the timezone to UTC for comparison
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        String expectedUtc = "'" + sdf.format(date) + "'";

        String result = sqlDialect.escapeDate(date, "UTC");
        assertEquals(expectedUtc, result);
    }

    @Test
    public void testEscapeDateWithNullTimezone() {
        SqlDialect sqlDialect = new SqlDialect();
        Date date = new Date(0); // Epoch time
        String expected = "'1970-01-01 01:00:00'"; // Default to Amsterdam

        String result = sqlDialect.escapeDate(date, null);
        assertEquals(expected, result);
    }

    @Test
    public void testEscapeByteArray() {
        SqlDialect sqlDialect = new SqlDialect();
        byte[] byteArray = {0x01, 0x02, 0x0A, 0x0F};
        String expected = "01020a0f";

        String result = sqlDialect.escapeByteArray(byteArray);
        assertEquals(expected, result);
    }

    @Test
    public void testEscapeEmptyByteArray() {
        SqlDialect sqlDialect = new SqlDialect();
        byte[] byteArray = {};
        String expected = "";

        String result = sqlDialect.escapeByteArray(byteArray);
        assertEquals(expected, result);
    }

    @Test
    public void testEscapeByteArrayWithSingleElement() {
        SqlDialect sqlDialect = new SqlDialect();
        byte[] byteArray = {0x0F};
        String expected = "0f";

        String result = sqlDialect.escapeByteArray(byteArray);
        assertEquals(expected, result);
    }

    @Test
    public void testEscapeByteArrayWithNegativeValues() {
        SqlDialect sqlDialect = new SqlDialect();
        byte[] byteArray = {-1, -2, -128};
        String expected = "fffe80";

        String result = sqlDialect.escapeByteArray(byteArray);
        assertEquals(expected, result);
    }
}