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
import static org.junit.Assert.assertTrue;

@RunWith(ValidatorJunitRunner.class)
public class PathValidatorTest {

    private static Logger logger = LoggerFactory.getLogger(PathValidatorTest.class);

    @Test
    public void semantic_error_when_path_parameter_not_defined_in_parameters_section_1() {
        SwaggerDeserializationResult swaggerResult = readDoc(
                RESOURCE_FOLDER + "path-parameter-not-defined-1.yml");
        List<SemanticError> errors = new SemanticValidationService(swaggerResult.getSwagger()).validate();
        logger.info(errors.toString());

        assertFalse(errors.isEmpty());
        assertEquals(1, errors.size());
        DefinitionSemanticError error = (DefinitionSemanticError) errors.get(0);
        assertTrue(error.getMessage().equals("Declared path parameter 'productId' needs to be defined as a path parameter at either the path or operation level"));
        assertEquals("paths./category/{id}/product/{productId}", error.getPath());
    }

    @Test
    public void semantic_error_when_path_parameter_not_defined_in_parameters_section_2() {
        SwaggerDeserializationResult swaggerResult = readDoc(
                RESOURCE_FOLDER + "path-parameter-not-defined-2.yml");
        List<SemanticError> errors = new SemanticValidationService(swaggerResult.getSwagger()).validate();
        logger.info(errors.toString());

        assertFalse(errors.isEmpty());
        assertEquals(1, errors.size());
        DefinitionSemanticError error = (DefinitionSemanticError) errors.get(0);
        assertTrue(error.getMessage().equals("Declared path parameter 'productId' needs to be defined as a path parameter at either the path or operation level"));
        assertEquals("paths./category/{id}/product/{productId}", error.getPath());
    }

    @Test
    public void semantic_error_when_path_parameter_not_defined_in_path_string() {
        SwaggerDeserializationResult swaggerResult = readDoc(
                RESOURCE_FOLDER + "path-parameter-not-defined-in-path.yml");
        List<SemanticError> errors = new SemanticValidationService(swaggerResult.getSwagger()).validate();
        logger.info(errors.toString());

        assertFalse(errors.isEmpty());
        assertEquals(1, errors.size());
        DefinitionSemanticError error = (DefinitionSemanticError) errors.get(0);
        assertTrue(error.getMessage().equals("Declared path parameter 'productId' needs to be defined as a path parameter at either the path or operation level"));
        assertEquals("paths./product/{id}.get.productId", error.getPath());
    }

    @Test
    public void semantic_error_when_path_parameter_not_marked_as_required_path_level() {
        SwaggerDeserializationResult swaggerResult = readDoc(
                RESOURCE_FOLDER + "path-parameter-not-required-on-path-level.yml");
        List<SemanticError> errors = new SemanticValidationService(swaggerResult.getSwagger()).validate();
        logger.info(errors.toString());

        assertFalse(errors.isEmpty());
        assertEquals(1, errors.size());
        DefinitionSemanticError error = (DefinitionSemanticError) errors.get(0);
        assertEquals("Path parameter 'id' must have 'required: true'.", error.getMessage());
        assertEquals("paths./product/{id}", error.getPath());
    }

    @Test
    public void semantic_error_when_path_parameter_not_marked_as_required_operation_level() {
        SwaggerDeserializationResult swaggerResult = readDoc(
                RESOURCE_FOLDER + "path-parameter-not-required-on-operation-level.yml");
        List<SemanticError> errors = new SemanticValidationService(swaggerResult.getSwagger()).validate();
        logger.info(errors.toString());

        assertFalse(errors.isEmpty());
        assertEquals(1, errors.size());
        DefinitionSemanticError error = (DefinitionSemanticError) errors.get(0);
        assertTrue(error.getMessage().equals("Path parameter 'id' must have 'required: true'."));
        assertEquals("paths./product/{id}.get.id", error.getPath());
    }

    @Test
    public void semantic_error_when_path_parameter_empty_in_path_string() {
        SwaggerDeserializationResult swaggerResult = readDoc(
                RESOURCE_FOLDER + "empty-path-parameter-in-path-string.yml");
        List<SemanticError> errors = new SemanticValidationService(swaggerResult.getSwagger()).validate();
        logger.info(errors.toString());

        assertFalse(errors.isEmpty());
        assertEquals(1, errors.size());
        DefinitionSemanticError error = (DefinitionSemanticError) errors.get(0);
        assertTrue(error.getMessage().equals("Empty path parameter declarations are not valid"));
        assertEquals("paths./product/{}", error.getPath());
    }

    @Test
    public void semantic_error_when_equivalent_path_strings() {
        SwaggerDeserializationResult swaggerResult = readDoc(RESOURCE_FOLDER + "equivalent-path-strings.yml");
        List<SemanticError> errors = new SemanticValidationService(swaggerResult.getSwagger()).validate();
        logger.info(errors.toString());

        assertFalse(errors.isEmpty());
        assertEquals(2, errors.size());
        DefinitionSemanticError error1 = (DefinitionSemanticError) errors.get(0);
        assertTrue(error1.getMessage().equals("Equivalent paths are not allowed."));
        assertEquals("paths./product/{productId}", error1.getPath());

        DefinitionSemanticError error2 = (DefinitionSemanticError) errors.get(1);
        assertTrue(error2.getMessage().equals("Equivalent paths are not allowed."));
        assertEquals("paths./product/{id}", error2.getPath());
    }

    @Test
    public void semantic_error_when_path_has_partial_templating() {
        SwaggerDeserializationResult swaggerResult = readDoc(
                RESOURCE_FOLDER + "partial-path-templating.yml");
        List<SemanticError> errors = new SemanticValidationService(swaggerResult.getSwagger()).validate();
        logger.info(errors.toString());

        assertFalse(errors.isEmpty());
        assertEquals(1, errors.size());
        DefinitionSemanticError error = (DefinitionSemanticError) errors.get(0);
        assertTrue(error.getMessage().equals("Partial path templating is not allowed."));
        assertEquals("paths./product/asd{id}", error.getPath());
    }

    @Test
    public void semantic_error_when_path_has_query_string() {
        SwaggerDeserializationResult swaggerResult = readDoc(
                RESOURCE_FOLDER + "path-with-query-string.yml");
        List<SemanticError> errors = new SemanticValidationService(swaggerResult.getSwagger()).validate();
        logger.info(errors.toString());

        assertFalse(errors.isEmpty());
        assertEquals(1, errors.size());
        DefinitionSemanticError error = (DefinitionSemanticError) errors.get(0);
        assertTrue(error.getMessage().equals("Query strings in paths are not allowed."));
        assertEquals("paths./product?asdasd", error.getPath());
    }

    @Test
    public void semantic_error_when_path_has_duplicated_parameters() {
        SwaggerDeserializationResult swaggerResult = readDoc(
                RESOURCE_FOLDER + "duplicate-parameters-in-path.yml");
        List<SemanticError> errors = new SemanticValidationService(swaggerResult.getSwagger()).validate();
        logger.info(errors.toString());

        assertFalse(errors.isEmpty());
        assertEquals(1, errors.size());
        DefinitionSemanticError error = (DefinitionSemanticError) errors.get(0);
        assertEquals("should not have duplicate items: [id]", error.getMessage());
        assertEquals("paths./category/{id}", error.getPath());
    }

    @Test
    public void semantic_error_when_operation_has_duplicated_parameters() {
        SwaggerDeserializationResult swaggerResult = readDoc(
                RESOURCE_FOLDER + "duplicate-parameters-in-operation.yml");
        List<SemanticError> errors = new SemanticValidationService(swaggerResult.getSwagger()).validate();
        logger.info(errors.toString());

        assertFalse(errors.isEmpty());
        assertEquals(1, errors.size());
        DefinitionSemanticError error = (DefinitionSemanticError) errors.get(0);
        assertEquals("should not have duplicate items: [product]", error.getMessage());
        assertEquals("paths./category/{id}/product/{productId}.get", error.getPath());
    }

}