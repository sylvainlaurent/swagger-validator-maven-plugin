package com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.path;

import com.github.sylvainlaurent.maven.swaggervalidator.ValidatorJunitRunner;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.error.DefinitionSemanticError;
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

@RunWith(ValidatorJunitRunner.class)
public class OperationValidatorTest {

    private static Logger logger = LoggerFactory.getLogger(OperationValidatorTest.class);

    @Test
    public void semantic_error_when_path_parameter_not_defined_in_parameters_section_1() {
        SwaggerDeserializationResult swaggerResult = readDoc(
                RESOURCE_FOLDER + "duplicate-operation-ids.yml");
        List<SemanticError> errors = new SemanticValidationService(swaggerResult.getSwagger()).validate();
        logger.info(errors.toString());

        assertFalse(errors.isEmpty());
        assertEquals(2, errors.size());
        DefinitionSemanticError error1 = (DefinitionSemanticError) errors.get(0);
        assertEquals("Operations must have unique operationIds. Duplicate: operation", error1.getMessage());
        assertEquals("paths./category/{id}/product/{productId}.post", error1.getPath());

        DefinitionSemanticError error2 = (DefinitionSemanticError) errors.get(1);
        assertEquals("Operations must have unique operationIds. Duplicate: operation", error1.getMessage());
        assertEquals("paths./category/{id}/product/{productId}.get", error2.getPath());
    }

    @Test
    public void semantic_error_when_operation_id_is_duplicated_across_different_paths() {
        SwaggerDeserializationResult swaggerResult = readDoc(
                RESOURCE_FOLDER + "duplicate-operation-ids-different-paths.yml");
        List<SemanticError> errors = new SemanticValidationService(swaggerResult.getSwagger()).validate();
        logger.info(errors.toString());

        assertFalse(errors.isEmpty());
        assertEquals(2, errors.size());
        DefinitionSemanticError error1 = (DefinitionSemanticError) errors.get(0);
        assertEquals("Operations must have unique operationIds. Duplicate: operation", error1.getMessage());
        assertEquals("paths./category/{id}/.get", error1.getPath());

        DefinitionSemanticError error2 = (DefinitionSemanticError) errors.get(1);
        assertEquals("Operations must have unique operationIds. Duplicate: operation", error1.getMessage());
        assertEquals("paths./category/{id}/product/{productId}.get", error2.getPath());
    }

    @Test
    public void semantic_error_when_more_than_one_body_parameter() {
        SwaggerDeserializationResult swaggerResult = readDoc(
                RESOURCE_FOLDER + "multiple-body-params.yml");
        List<SemanticError> errors = new SemanticValidationService(swaggerResult.getSwagger()).validate();
        logger.info(errors.toString());

        assertFalse(errors.isEmpty());
        assertEquals(1, errors.size());
        DefinitionSemanticError error = (DefinitionSemanticError) errors.get(0);
        assertEquals("Multiple body parameters are not allowed.", error.getMessage());
        assertEquals("paths./category/{id}/product/{productId}.post", error.getPath());
    }

    @Test
    public void semantic_error_when_both_body_and_formData_parameter() {
        SwaggerDeserializationResult swaggerResult = readDoc(
                RESOURCE_FOLDER + "both-form-and-body-params.yml");
        List<SemanticError> errors = new SemanticValidationService(swaggerResult.getSwagger()).validate();
        logger.info(errors.toString());

        assertFalse(errors.isEmpty());
        assertEquals(1, errors.size());
        DefinitionSemanticError error = (DefinitionSemanticError) errors.get(0);
        assertEquals("Parameters cannot contain both 'body' and 'formData' types.", error.getMessage());
        assertEquals("paths./categories.post", error.getPath());
    }
}