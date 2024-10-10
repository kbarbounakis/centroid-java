package com.centroid.query;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class WhereExpressionTest {

    @Test
    public void testEqual() {
        WhereExpression whereExpression = new WhereExpression("field").equal("value");

        QueryElement<String, Object> expectedElement = new QueryElement<>("$eq", new Object[]{
            new QueryElement<>("field", 1),
            "value"
        });

        assertNotNull(whereExpression.whereElement);
        assertEquals(expectedElement.getKey(), whereExpression.whereElement.getKey());
        Object[] expected = (Object[]) expectedElement.getValue();
        Object[] actual = (Object[]) whereExpression.whereElement.getValue();
        assertEquals(expected[0], actual[0]);
    }

    @Test
    public void testEqualWithNullValue() {
        WhereExpression whereExpression = new WhereExpression("field");
        whereExpression.equal(null);

        QueryElement<String, Object> expectedElement = new QueryElement<>("$eq", new Object[]{
            new QueryElement<>("field", 1),
            null
        });

        assertNotNull(whereExpression.whereElement);
        assertEquals(expectedElement.getKey(), whereExpression.whereElement.getKey());
        assertArrayEquals((Object[]) expectedElement.getValue(), (Object[]) whereExpression.whereElement.getValue());
    }

    @Test
    public void testEqualWithMultipleConditions() {
        WhereExpression whereExpression = new WhereExpression("field1");
        whereExpression.equal("value1").and("field2").equal("value2");

        QueryElement<String, Object> expectedElement = new QueryElement<>("$and", new Object[]{
            new QueryElement<>("$eq", new Object[]{
                new QueryElement<>("field1", 1),
                "value1"
            }),
            new QueryElement<>("$eq", new Object[]{
                new QueryElement<>("field2", 1),
                "value2"
            })
        });

        assertNotNull(whereExpression.whereElement);
        assertEquals(expectedElement.getKey(), whereExpression.whereElement.getKey());
        assertArrayEquals((Object[]) expectedElement.getValue(), (Object[]) whereExpression.whereElement.getValue());
    }
}