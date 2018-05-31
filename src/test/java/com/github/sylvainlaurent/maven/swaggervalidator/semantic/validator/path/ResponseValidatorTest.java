package com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.path;

import static com.github.sylvainlaurent.maven.swaggervalidator.SemanticValidationServiceTest.RESOURCE_FOLDER;
import static com.github.sylvainlaurent.maven.swaggervalidator.SemanticValidationServiceTest.readDoc;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.sylvainlaurent.maven.swaggervalidator.ValidatorJunitRunner;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.error.SchemaError;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.error.SemanticError;
import com.github.sylvainlaurent.maven.swaggervalidator.service.SemanticValidationService;

import io.swagger.parser.util.SwaggerDeserializationResult;

@RunWith(ValidatorJunitRunner.class)
public class ResponseValidatorTest {

    private static Logger logger = LoggerFactory.getLogger(ResponseValidatorTest.class);

    @Test
    public void definitions_semantic_validation_should_fail_no_description_in_response() {
        SwaggerDeserializationResult swaggerResult = readDoc(RESOURCE_FOLDER + "no-description-in-responses.yml");
        List<SemanticError> errors = new SemanticValidationService(swaggerResult.getSwagger()).validate();
        logger.info(errors.toString());

        assertFalse(errors.isEmpty());
        assertEquals(1, errors.size());
        SchemaError error = (SchemaError) errors.get(0);
        assertTrue(error.getMessage().contains("missingProperty: 'description'"));
        assertEquals("paths./category/{id}/product/{productId}.get.responses.200", error.getPath());
    }

    @Test
    public void path_error_should_fail_when_responses_missing() {
        SwaggerDeserializationResult swaggerResult = readDoc(RESOURCE_FOLDER + "missing-responses.yml");
        List<SemanticError> errors = new SemanticValidationService(swaggerResult.getSwagger()).validate();
        logger.info(errors.toString());

        assertFalse(errors.isEmpty());
        assertEquals(1, errors.size());
        SchemaError error = (SchemaError) errors.get(0);
        assertTrue(error.getMessage().contains("missingProperty: 'responses'"));
        assertEquals("paths./category/{id}/product/{productId}.get", error.getPath());
    }

    @Test
    public void path_error_should_fail_when_schema_has_array_with_missing_items() {
        SwaggerDeserializationResult swaggerResult = readDoc(
                RESOURCE_FOLDER + "schema-with-array-should-have-items.yml");
        List<SemanticError> errors = new SemanticValidationService(swaggerResult.getSwagger()).validate();
        logger.info(errors.toString());

        assertFalse(errors.isEmpty());
        assertEquals(1, errors.size());
        SchemaError error = (SchemaError) errors.get(0);
        assertTrue(error.getMessage().contains("'type: array', require a sibling 'items:' field"));
        assertEquals("paths./category/{id}/product/{productId}.get.responses.200.schema", error.getPath());
    }

    @Test
    public void succeed_when_array_items_is_simple_type() {
        SwaggerDeserializationResult swaggerResult = readDoc(RESOURCE_FOLDER + "array-with-simple-items.yml");
        List<SemanticError> errors = new SemanticValidationService(swaggerResult.getSwagger()).validate();
        logger.info(errors.toString());

        assertTrue(errors.isEmpty());
    }

    @Test
    public void succeed_when_response_is_ref_to_array_model() {
        SwaggerDeserializationResult swaggerResult = readDoc(RESOURCE_FOLDER + "array-model-valid.yml");
        List<SemanticError> errors = new SemanticValidationService(swaggerResult.getSwagger()).validate();
        logger.info(errors.toString());

        assertTrue(errors.isEmpty());
    }

    @Test
    public void path_error_should_fail_when_schema_has_array_with_empty_items() {
        SwaggerDeserializationResult swaggerResult = readDoc(
                RESOURCE_FOLDER + "schema-with-array-should-have-non-empty-items.yml");
        List<SemanticError> errors = new SemanticValidationService(swaggerResult.getSwagger()).validate();
        logger.info(errors.toString());

        assertFalse(errors.isEmpty());
        assertEquals(1, errors.size());
        SchemaError error = (SchemaError) errors.get(0);
        assertTrue(error.getMessage().contains("'type: array', require a sibling 'items:' field"));
        assertEquals("paths./category/{id}/product/{productId}.get.responses.200.schema", error.getPath());
    }

}