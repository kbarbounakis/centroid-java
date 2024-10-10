package com.centroid.query;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import java.util.LinkedHashMap;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class QueryExpressionTest {

    @Test
    public void shouldCreateSelectExpression() throws JsonProcessingException {
        QueryExpression expr = new QueryExpression().select("id", "name");
        String json = new ObjectMapper().writeValueAsString(expr);
        assertEquals(json, "{\"$select\":{\"id\":1,\"name\":1}}");
        expr = new ObjectMapper().readValue(json, QueryExpression.class);
        LinkedHashMap<String, Object> selectElement = expr.getSelect();
        assertEquals(selectElement.size(), 2);
        assertEquals(selectElement.get("id"), 1);
        assertEquals(selectElement.get("name"), 1);
    }

    @Test
    public void testGetSelectElementEmpty() {
        QueryExpression expr = new QueryExpression();
        LinkedHashMap<String, Object> selectElement = expr.getSelect();
        assertTrue(selectElement.isEmpty());
    }

    @Test
    public void testGetSelectElementWithFields() {
        QueryExpression expr = new QueryExpression().select("field1", "field2");
        LinkedHashMap<String, Object> selectElement = expr.getSelect();
        assertEquals(selectElement.size(), 2);
        assertEquals(selectElement.get("field1"), 1);
        assertEquals(selectElement.get("field2"), 1);
    }

    @Test
    public void testGetSelectElementWithQueryFields() {
        QueryField field1 = new QueryField("field1", 1);
        QueryField field2 = new QueryField("field2", 1);
        QueryExpression expr = new QueryExpression().select(field1, field2);
        LinkedHashMap<String, Object> selectElement = expr.getSelect();
        assertEquals(selectElement.size(), 2);
        assertEquals(selectElement.get("field1"), 1);
        assertEquals(selectElement.get("field2"), 1);
    }
}
