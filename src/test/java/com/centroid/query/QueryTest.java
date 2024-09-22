package com.centroid.query;

import static org.junit.jupiter.api.Assertions.assertEquals;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import org.junit.jupiter.api.Test;
import io.reactivex.rxjava3.core.*;
import java.util.concurrent.atomic.AtomicInteger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * Unit test for simple App.
 */
public class QueryTest {

    @Test
    public void shouldCreateField() throws JsonProcessingException {
        QueryField field = new QueryField("id");
        assertEquals("id", field.getName());
        String json = new ObjectMapper().writeValueAsString(field);
        assertEquals("{\"id\":1}", json);
    }

    @Test
    public void shouldUseFlowable() {
        Flowable<String> source = Flowable.just("Hello world");
        AtomicInteger count = new AtomicInteger();
        Disposable subscription1 = source.subscribe(s -> {
            Thread.sleep(1000);
            count.getAndIncrement();
        });
        Disposable subscription2 = source.subscribe(s -> {
            assertEquals(1, count.get());
            count.getAndIncrement();
        });
        assertEquals(2, count.get());
        subscription1.dispose();
        subscription2.dispose();
    }

    @Test
    public void shouldUseBehaviorSubject() {
        BehaviorSubject<String> source = BehaviorSubject.create();
        AtomicInteger count = new AtomicInteger();
        Disposable subscription1 = source.subscribe(s -> {
            Thread.sleep(1000);
            count.getAndIncrement();
        });
        Disposable subscription2 = source.subscribe(s -> {
            assertEquals(1, count.get());
            count.getAndIncrement();
        });
        source.onNext("Hello world");
        assertEquals(2, count.get());
        subscription1.dispose();
        subscription2.dispose();
    }

}
