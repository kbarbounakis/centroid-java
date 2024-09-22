package com.centroid.query;

import static org.junit.jupiter.api.Assertions.assertEquals;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import org.junit.jupiter.api.Test;
import io.reactivex.rxjava3.core.*;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * Unit test for simple App.
 */
public class QueryTest {

    @Test
    public void shouldCreateField() {
        QueryField field = new QueryField("id").from("users");
        assertEquals("id", field.getName());
        assertEquals("users", field.getCollection());
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
