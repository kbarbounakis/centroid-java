package com.centroid.query;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ObjectNameValidatorTest {

    @Test
    public void shouldCreateInstance() {
        ObjectNameValidator validator = new ObjectNameValidator();
        assertEquals(ObjectNameValidator.Default, validator.pattern);
    }

    @Test
    public void shouldValidateName() {
        ObjectNameValidator validator = new ObjectNameValidator();
        boolean result = validator.test("name", true, false);
        assertEquals(true, result);
        result = validator.test("test name", true, false);
        assertEquals(false, result);
        result = validator.test("another_name", true, false);
        assertEquals(true, result);
        result = validator.test("users.id", true, false);
        assertEquals(true, result);
    }

    @Test
    public void shouldEscapeName() {
        ObjectNameValidator validator = new ObjectNameValidator();
        assertEquals("`name`", validator.escape("name", "`$1`"));
        assertEquals("`users`.`id`", validator.escape("users.id", "`$1`"));
    }

}
