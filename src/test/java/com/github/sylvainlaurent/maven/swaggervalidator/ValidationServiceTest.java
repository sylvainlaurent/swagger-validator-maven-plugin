
package com.github.sylvainlaurent.maven.swaggervalidator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

public class ValidationServiceTest {
    private final ValidationService service = new ValidationService();

    @Test
    public void test_empty_file_yml() {
        final ValidationResult result = service.validate(new File("src/test/resources/swagger-doc-empty.yml"));
        assertTrue(result.hasError());
        assertEquals(1, result.getMessages().size());
    }

    @Test
    public void test_empty_file_json() {
        final ValidationResult result = service.validate(new File("src/test/resources/swagger-doc-empty.json"));
        assertTrue(result.hasError());
        assertEquals(1, result.getMessages().size());
    }

    @Test
    public void test_malformed_file_yml() {
        final ValidationResult result = service.validate(new File("src/test/resources/swagger-doc-malformed.yml"));
        assertTrue(result.hasError());
        assertEquals(1, result.getMessages().size());
    }

    @Test
    public void test_malformed_file_json() {
        final ValidationResult result = service.validate(new File("src/test/resources/swagger-doc-malformed.json"));
        assertTrue(result.hasError());
        assertEquals(1, result.getMessages().size());
    }

    @Test
    public void test_not_swagger_yml() {
        final ValidationResult result = service.validate(new File("src/test/resources/swagger-doc-not-swagger.yml"));
        assertTrue(result.hasError());
        assertFalse(result.getMessages().isEmpty());
    }

    @Test
    public void test_not_swagger_json() {
        final ValidationResult result = service.validate(new File("src/test/resources/swagger-doc-not-swagger.json"));
        assertTrue(result.hasError());
        assertFalse(result.getMessages().isEmpty());
    }

    @Test
    public void test_not_valid_swagger_yml() {
        final ValidationResult result = service.validate(new File("src/test/resources/swagger-doc-not-valid.yml"));
        assertTrue(result.hasError());
        assertEquals(1, result.getMessages().size());
    }

    @Test
    public void test_not_valid_swagger_json() {
        final ValidationResult result = service.validate(new File("src/test/resources/swagger-doc-not-valid.json"));
        assertTrue(result.hasError());
        assertEquals(1, result.getMessages().size());
    }

    @Test
    public void test_valid_swagger() {
        final ValidationResult result = service.validate(new File("src/test/resources/swagger-editor-example.yml"));
        assertFalse(result.hasError());
        assertTrue(result.getMessages().isEmpty());
    }

    @Test
    public void test_valid_swagger_json() {
        final ValidationResult result = service.validate(new File("src/test/resources/swagger-editor-example.json"));
        assertFalse(result.hasError());
        assertTrue(result.getMessages().isEmpty());
    }
}
