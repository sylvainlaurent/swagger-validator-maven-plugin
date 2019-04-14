package com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.path;

import com.github.sylvainlaurent.maven.swaggervalidator.ValidatorJunitRunner;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.error.SemanticError;
import com.github.sylvainlaurent.maven.swaggervalidator.service.SemanticValidationService;
import io.swagger.parser.util.SwaggerDeserializationResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.github.sylvainlaurent.maven.swaggervalidator.SemanticValidationServiceTest.RESOURCE_FOLDER;
import static com.github.sylvainlaurent.maven.swaggervalidator.SemanticValidationServiceTest.readDoc;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(ValidatorJunitRunner.class)
public class OperationParametersReferenceValidatorTest {

    private static Logger logger = LoggerFactory.getLogger(OperationParametersReferenceValidatorTest.class);

    @Test
    public void semantic_error_when_query_parameter_reference_is_invalid() {
        SwaggerDeserializationResult swaggerResult = readDoc(
                RESOURCE_FOLDER + "invalid-query-parameter-reference.yml");
        List<SemanticError> errors = new SemanticValidationService(swaggerResult.getSwagger()).validate();
        logger.info(errors.toString());

        assertFalse(errors.isEmpty());
        assertEquals(1, errors.size());
        SemanticError error = errors.get(0);
        assertTrue(error.getMessage().contains("Could not resolve reference: #/parameters/q_category_"));
        assertEquals("paths./category.get.#/parameters/q_category_", error.getPath());
    }

}