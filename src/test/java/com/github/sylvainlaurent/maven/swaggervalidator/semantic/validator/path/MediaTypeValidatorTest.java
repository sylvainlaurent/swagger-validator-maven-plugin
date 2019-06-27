package com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.path;

import static com.github.sylvainlaurent.maven.swaggervalidator.SemanticValidationServiceTest.readDoc;
import static org.junit.Assert.*;

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
public class MediaTypeValidatorTest {

    public static final String RESOURCE_FOLDER = "src/test/resources/semantic-validation/";
    private static Logger logger = LoggerFactory.getLogger(SemanticValidationServiceTest.class);

    @Test
    public void success_when_valid_mime_types_in_consumes_and_produces() {
        SwaggerDeserializationResult swaggerResult = readDoc(
                RESOURCE_FOLDER + "consumes-and-produces-valid-MIME-types.yml");
        List<SemanticError> errors = new SemanticValidationService(swaggerResult.getSwagger()).validate();
        logger.info(errors.toString());
        assertTrue(errors.isEmpty());
    }

    @Test
    public void should_fail_when_invalid_mime_types_are_present_int_the_spec() {
        SwaggerDeserializationResult swaggerResult = readDoc(
                RESOURCE_FOLDER + "consumes-and-produces-invalid-MIME-types.yml");
        List<SemanticError> errors = new SemanticValidationService(swaggerResult.getSwagger()).validate();
        logger.info(errors.toString());
        assertEquals("Should have 3 validation errors",3, errors.size());
        assertEquals("invalid media type 'application////json' in 'produces'", errors.get(0).getMessage());
        assertEquals("invalid media type 'not_allowed_prefix/application/mytype3' in 'consumes'", errors.get(1).getMessage());
        assertEquals("paths./producs.post", errors.get(1).getPath());
        assertEquals("invalid media type 'application-json' in 'consumes'", errors.get(2).getMessage());
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
    public void media_type_multipart_formdata_should_be_accepted() {
        SwaggerDeserializationResult swaggerResult = readDoc(
                RESOURCE_FOLDER + "consumes-and-produces-with-multipart-formdata-mime-type.yml");
        List<SemanticError> errors = new SemanticValidationService(swaggerResult.getSwagger()).validate();
        logger.info(errors.toString());
        assertTrue(errors.isEmpty());
    }
}
