package com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.path;

import static com.github.sylvainlaurent.maven.swaggervalidator.SemanticValidationServiceTest.readDoc;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import com.github.sylvainlaurent.maven.swaggervalidator.SemanticValidationServiceTest;
import com.github.sylvainlaurent.maven.swaggervalidator.ValidatorJunitRunner;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.error.SemanticError;
import com.github.sylvainlaurent.maven.swaggervalidator.service.SemanticValidationService;
import io.swagger.parser.util.SwaggerDeserializationResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(ValidatorJunitRunner.class)
public class MimeTypeValidatorTest {


    public static final String RESOURCE_FOLDER = "src/test/resources/semantic-validation/";
    private static Logger logger = LoggerFactory.getLogger(SemanticValidationServiceTest.class);

    @Test
    public void fail_when_invalid_mime_types_in_consumes_and_produces() {
        SwaggerDeserializationResult swaggerResult = readDoc(
                RESOURCE_FOLDER + "consumes-and-produces-invalid-MIME-types.yml");
        List<SemanticError> errors = new SemanticValidationService(swaggerResult.getSwagger()).validate();
        logger.info(errors.toString());
        assertFalse(errors.isEmpty());
        assertTrue(errors.contains(new SemanticError("paths./producs.post", "invalid MIME type 'application/mytype4' in 'produces'")));
        assertTrue(errors.contains(new SemanticError("paths./producs.post", "invalid MIME type 'application/mytype3' in 'consumes'")));
        assertTrue(errors.contains(new SemanticError("", "invalid MIME type 'application/mytype2' in 'consumes'")));
        assertTrue(errors.contains(new SemanticError("", "invalid MIME type 'application/mytype1' in 'produces'")));
    }

    @Test
    public void succes_when_valid_mime_types_in_consumes_and_produces() {
        SwaggerDeserializationResult swaggerResult = readDoc(
                RESOURCE_FOLDER + "consumes-and-produces-valid-MIME-types.yml");
        List<SemanticError> errors = new SemanticValidationService(swaggerResult.getSwagger()).validate();
        logger.info(errors.toString());
        assertTrue(errors.isEmpty());
    }

    @Test
    public void fail_when_consumes_and_produces_are_absent_at_all() {
        SwaggerDeserializationResult swaggerResult = readDoc(
                RESOURCE_FOLDER + "consumes-and-produces-absent-at-all.yml");
        List<SemanticError> errors = new SemanticValidationService(swaggerResult.getSwagger()).validate();
        logger.info(errors.toString());
        assertFalse(errors.isEmpty());
        assertTrue(errors.contains(new SemanticError("paths./producs.post", "'produces' cannot be empty")));
        assertTrue(errors.contains(new SemanticError("paths./producs.post", "'consumes' cannot be empty")));
        assertTrue(errors.contains(new SemanticError("paths./producs.get", "'produces' cannot be empty")));
    }

    @Test
    public void success_when_consumes_and_produces_are_absent_in_swagger_root_but_present_in_operations() {
        SwaggerDeserializationResult swaggerResult = readDoc(
                RESOURCE_FOLDER + "consumes-and-produces-absent-in-root.yml");
        List<SemanticError> errors = new SemanticValidationService(swaggerResult.getSwagger()).validate();
        logger.info(errors.toString());
        assertTrue(errors.isEmpty());
    }


    @Test
    public void fail_when_consumes_and_produces_are_missed_or_invalid_in_operations() {
        SwaggerDeserializationResult swaggerResult = readDoc(
                RESOURCE_FOLDER + "consumes-and-produces-invalid-values-in-operations.yml");
        List<SemanticError> errors = new SemanticValidationService(swaggerResult.getSwagger()).validate();
        logger.info(errors.toString());
        assertFalse(errors.isEmpty());
        assertTrue(errors.contains(new SemanticError("paths./producs.post", "'produces' cannot be empty")));
        assertTrue(errors.contains(new SemanticError("paths./producs.post", "'consumes' cannot be empty")));
        assertTrue(errors.contains(new SemanticError("paths./producs.get", "'consumes' must be equal to 'consumes: []' ")));
        assertTrue(errors.contains(new SemanticError("paths./producs.get", "'produces' cannot be empty")));
    }
}
