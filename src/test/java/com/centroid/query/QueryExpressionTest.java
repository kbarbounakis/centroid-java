package com.centroid.query;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Unit test for simple App.
 */
public class QueryExpressionTest {

    @Test
    public void shouldCreateSelectExpression() throws JsonProcessingException {
        QueryExpression expr = new QueryExpression().select("id", "name");
        String json = new ObjectMapper().writeValueAsString(expr);
        assertEquals(json, "{\"$select\":{\"id\":1,\"name\":1}}");
        expr = new ObjectMapper().readValue(json, QueryExpression.class);
        LinkedHashMap<String, Object> selectElement = expr.getSelectElement();
        assertEquals(selectElement.size(), 2);
        assertEquals(selectElement.get("id"), 1);
        assertEquals(selectElement.get("name"), 1);

    }

}
