package com.github.sylvainlaurent.maven.swaggervalidator.semantic;

import static com.github.sylvainlaurent.maven.swaggervalidator.semantic.error.SemanticError.ErrorType.API_PATH_IS_NOT_UNIQUE;
import static com.github.sylvainlaurent.maven.swaggervalidator.semantic.error.SemanticError.ErrorType.DISCRIMINATOR_NOT_DEFINED_AS_PROPERTY;
import static com.github.sylvainlaurent.maven.swaggervalidator.semantic.error.SemanticError.ErrorType.DISCRIMINATOR_NOT_DEFINED_AS_REQUIRED_PROPERTY;
import static com.github.sylvainlaurent.maven.swaggervalidator.semantic.error.SemanticError.ErrorType.ITEMS_PROPERTY_IS_NOT_DEFINED_IN_ARRAY;
import static com.github.sylvainlaurent.maven.swaggervalidator.semantic.error.SemanticError.ErrorType.OPERATION_CONTAINS_BOTH_FORM_AND_BODY_PARAMETER;
import static com.github.sylvainlaurent.maven.swaggervalidator.semantic.error.SemanticError.ErrorType.OPERATION_CONTAINS_DUPLICATE_PARAMETERS;
import static com.github.sylvainlaurent.maven.swaggervalidator.semantic.error.SemanticError.ErrorType.OPERATION_CONTAINS_MULTIPLE_BODY_PARAMETERS;
import static com.github.sylvainlaurent.maven.swaggervalidator.semantic.error.SemanticError.ErrorType.PATH_PARAMS_DONT_MATCH_DEFINED_OPERATION_PARAMS;
import static com.github.sylvainlaurent.maven.swaggervalidator.semantic.error.SemanticError.ErrorType.PROPERTIES_ALREADY_DEFINED_IN_ANCESTORS;
import static com.github.sylvainlaurent.maven.swaggervalidator.semantic.error.SemanticError.ErrorType.REFERENCE_DOESNT_POINT_TO_AN_EXISTING_DEFINITION;
import static com.github.sylvainlaurent.maven.swaggervalidator.semantic.error.SemanticError.ErrorType.REQUIRED_PROPERTIES_ARE_DUPLICATED;
import static com.github.sylvainlaurent.maven.swaggervalidator.semantic.error.SemanticError.ErrorType.REQUIRED_PROPERTIES_NOT_DEFINED_AS_OBJECT_PROPERTIES;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.github.sylvainlaurent.maven.swaggervalidator.ValidatorJunitRunner;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.error.DefinitionsSemanticError;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.error.SemanticError;

import io.swagger.models.auth.AuthorizationValue;
import io.swagger.parser.Swagger20Parser;
import io.swagger.parser.util.SwaggerDeserializationResult;

@RunWith(ValidatorJunitRunner.class)
public class SemanticValidatorTest {

    @Test
    public void operations_semantic_validation_should_fail_when_two_path_are_equal() {
        final SwaggerDeserializationResult swaggerResult = readDoc("src/test/resources/semantic-validation/swagger-doc-equivalent-operations.yml");
        SemanticValidationResult result = new SemanticValidator(swaggerResult.getSwagger()).validate();
        assertTrue(result.hasErrors());
        List<SemanticError> semanticErrors = result.getErrors();
        assertEquals(1, semanticErrors.size());
        assertEquals(API_PATH_IS_NOT_UNIQUE, semanticErrors.get(0).getErrorType());
    }

    @Test
    public void operations_semantic_validation_should_fail_when_an_operations_contains_both_form_and_body_params() {
        final SwaggerDeserializationResult swaggerResult = readDoc("src/test/resources/semantic-validation/swagger-doc-both-form-and-body-params.yml");
        SemanticValidationResult result = new SemanticValidator(swaggerResult.getSwagger()).validate();
        assertTrue(result.hasErrors());
        List<SemanticError> semanticErrors = result.getErrors();
        assertEquals(1, semanticErrors.size());
        assertEquals(OPERATION_CONTAINS_BOTH_FORM_AND_BODY_PARAMETER, semanticErrors.get(0).getErrorType());
    }

    @Test
    public void operations_semantic_validation_should_fail_when_operation_params_contains_multiple_body_params() {
        final SwaggerDeserializationResult swaggerResult = readDoc("src/test/resources/semantic-validation/swagger-doc-multiple-body-params.yml");
        SemanticValidationResult result = new SemanticValidator(swaggerResult.getSwagger()).validate();

        assertTrue(result.hasErrors());
        List<SemanticError> semanticErrors = result.getErrors();

        assertEquals(1, semanticErrors.size());
        assertEquals(OPERATION_CONTAINS_MULTIPLE_BODY_PARAMETERS, semanticErrors.get(0).getErrorType());
    }

    @Test
    public void operations_semantic_validation_should_fail_when_path_variables_are_not_matched_by_operation_params() {
        final SwaggerDeserializationResult swaggerResult = readDoc("src/test/resources/semantic-validation/swagger-doc-unmatched-path-variable.yml");
        SemanticValidationResult result = new SemanticValidator(swaggerResult.getSwagger()).validate();

        assertTrue(result.hasErrors());
        List<SemanticError> semanticErrors = result.getErrors();

        assertEquals(1, semanticErrors.size());
        assertEquals(PATH_PARAMS_DONT_MATCH_DEFINED_OPERATION_PARAMS, semanticErrors.get(0).getErrorType());
    }

    @Test
    public void operations_semantic_validation_should_fail_when_operation_params_are_not_matched_by_path_variables() {
        final SwaggerDeserializationResult swaggerResult = readDoc("src/test/resources/semantic-validation/swagger-doc-unmatched-operation-path-parameter.yml");
        SemanticValidationResult result = new SemanticValidator(swaggerResult.getSwagger()).validate();

        assertTrue(result.hasErrors());
        List<SemanticError> semanticErrors = result.getErrors();

        assertEquals(1, semanticErrors.size());
        assertEquals(PATH_PARAMS_DONT_MATCH_DEFINED_OPERATION_PARAMS, semanticErrors.get(0).getErrorType());
    }

    @Test
    public void operations_semantic_validation_should_fail_when_operations_contains_parameters_with_same_name_and_in() {
        final SwaggerDeserializationResult swaggerResult = readDoc("src/test/resources/semantic-validation/swagger-doc-duplicate-parameters-in-operation.yml");
        SemanticValidationResult result = new SemanticValidator(swaggerResult.getSwagger()).validate();

        assertTrue(result.hasErrors());
        List<SemanticError> semanticErrors = result.getErrors();

        assertEquals(1, semanticErrors.size());
        assertEquals(OPERATION_CONTAINS_DUPLICATE_PARAMETERS, semanticErrors.get(0).getErrorType());
    }

    @Test
    public void definitions_semantic_validation_should_fail_when_definitions_contains_undefined_reference() {
        final SwaggerDeserializationResult swaggerResult = readDoc("src/test/resources/semantic-validation/swagger-doc-reference-not-found-in-definitions.yml");
        SemanticValidationResult result = new SemanticValidator(swaggerResult.getSwagger()).validate();

        assertTrue(result.hasErrors());
        List<SemanticError> semanticErrors = result.getErrors();

        List<String> expectedErrorPaths = asList("Test.undefRef2", "Test.array.items.undefRef1.items");
        assertEquals(expectedErrorPaths.size(), semanticErrors.size());

        DefinitionsSemanticError semanticError1 = (DefinitionsSemanticError) semanticErrors.get(0);
        DefinitionsSemanticError semanticError2 = (DefinitionsSemanticError) semanticErrors.get(1);

        List<String> errorPaths = asList(semanticError1.getPath(), semanticError1.getPath());
        assertTrue(expectedErrorPaths.containsAll(errorPaths));

        assertEquals(REFERENCE_DOESNT_POINT_TO_AN_EXISTING_DEFINITION, semanticError1.getErrorType());
        assertEquals(REFERENCE_DOESNT_POINT_TO_AN_EXISTING_DEFINITION, semanticError2.getErrorType());
    }

    @Test
    public void definitions_semantic_validation_should_fail_when_model_contains_required_properties_not_defined() {
        final SwaggerDeserializationResult swaggerResult = readDoc("src/test/resources/semantic-validation/swagger-doc-required-properties-not-defined.yml");
        SemanticValidationResult result = new SemanticValidator(swaggerResult.getSwagger()).validate();

        assertTrue(result.hasErrors());
        List<SemanticError> semanticErrors = result.getErrors();
        assertEquals(1, semanticErrors.size());
        DefinitionsSemanticError semanticError = (DefinitionsSemanticError)semanticErrors.get(0);
        assertEquals(REQUIRED_PROPERTIES_NOT_DEFINED_AS_OBJECT_PROPERTIES, semanticError.getErrorType());
        assertEquals("Product", semanticError.getPath());
    }

    @Test
    public void definitions_semantic_validation_should_fail_when_object_property_contains_required_properties_not_defined() {

        final SwaggerDeserializationResult swaggerResult = readDoc(
            "src/test/resources/semantic-validation/swagger-doc-required-properties-not-defined-for-object-property.yml");
        SemanticValidationResult result = new SemanticValidator(swaggerResult.getSwagger()).validate();

        assertTrue(result.hasErrors());
        List<SemanticError> semanticErrors = result.getErrors();
        assertEquals(1, semanticErrors.size());
        DefinitionsSemanticError semanticError = (DefinitionsSemanticError)semanticErrors.get(0);
        assertEquals(REQUIRED_PROPERTIES_NOT_DEFINED_AS_OBJECT_PROPERTIES, semanticError.getErrorType());
        assertEquals("Product.category.image", semanticError.getPath());
    }

    @Test
    public void definitions_semantic_validation_should_fail_when_model_contains_duplicated_required_properties() {
        final SwaggerDeserializationResult swaggerResult = readDoc("src/test/resources/semantic-validation/swagger-doc-required-properties-duplicated.yml");
        SemanticValidationResult result = new SemanticValidator(swaggerResult.getSwagger()).validate();

        assertTrue(result.hasErrors());
        List<SemanticError> semanticErrors = result.getErrors();
        assertEquals(1, semanticErrors.size());
        DefinitionsSemanticError semanticError = (DefinitionsSemanticError)semanticErrors.get(0);
        assertEquals(REQUIRED_PROPERTIES_ARE_DUPLICATED, semanticError.getErrorType());
        assertEquals("Product", semanticError.getPath());
    }

    @Test
    public void definitions_semantic_validation_should_fail_when_object_property_contains_duplicated_required_properties() {

        final SwaggerDeserializationResult swaggerResult = readDoc(
            "src/test/resources/semantic-validation/swagger-doc-required-properties-duplicated-for-object-property.yml");
        SemanticValidationResult result = new SemanticValidator(swaggerResult.getSwagger()).validate();

        assertTrue(result.hasErrors());
        List<SemanticError> semanticErrors = result.getErrors();
        assertEquals(1, semanticErrors.size());
        DefinitionsSemanticError semanticError = (DefinitionsSemanticError)semanticErrors.get(0);
        assertEquals(REQUIRED_PROPERTIES_ARE_DUPLICATED, semanticError.getErrorType());
        assertEquals("Product.category", semanticError.getPath());
    }

    @Test
    public void definitions_semantic_validation_should_fail_when_model_contains_a_discrimininator_not_defined_as_property() {
        final SwaggerDeserializationResult swaggerResult = readDoc(
            "src/test/resources/semantic-validation/swagger-doc-discriminator-not-defined-as-property" + ".yml");
        SemanticValidationResult result = new SemanticValidator(swaggerResult.getSwagger()).validate();

        assertTrue(result.hasErrors());
        List<SemanticError> semanticErrors = result.getErrors();
        assertEquals(2, semanticErrors.size());

        DefinitionsSemanticError error1 = (DefinitionsSemanticError)semanticErrors.get(0);
        DefinitionsSemanticError error2 = (DefinitionsSemanticError)semanticErrors.get(1);
        assertEquals(DISCRIMINATOR_NOT_DEFINED_AS_PROPERTY, error1.getErrorType());
        assertEquals("Product", error1.getPath());
        assertEquals(DISCRIMINATOR_NOT_DEFINED_AS_REQUIRED_PROPERTY, error2.getErrorType());
        assertEquals("Product", error2.getPath());
    }

    @Test
    public void definitions_semantic_validation_should_fail_when_model_contains_a_discrimininator_not_defined_as_required_property() {
        final SwaggerDeserializationResult swaggerResult = readDoc(
            "src/test/resources/semantic-validation/swagger-doc-discriminator-not-defined-as-required-property.yml");
        SemanticValidationResult result = new SemanticValidator(swaggerResult.getSwagger()).validate();

        assertTrue(result.hasErrors());
        List<SemanticError> semanticErrors = result.getErrors();
        assertEquals(1, semanticErrors.size());
        DefinitionsSemanticError error = (DefinitionsSemanticError)semanticErrors.get(0);
        assertEquals(DISCRIMINATOR_NOT_DEFINED_AS_REQUIRED_PROPERTY, error.getErrorType());
        assertEquals("Product", error.getPath());
    }

    @Test
    public void definitions_semantic_validation_should_fail_when_model_contains_a_property_already_defined_in_ancestors() {
        final SwaggerDeserializationResult swaggerResult = readDoc(
            "src/test/resources/semantic-validation/swagger-doc-property_already_defined_in_ancestors" + ".yml");
        SemanticValidationResult result = new SemanticValidator(swaggerResult.getSwagger()).validate();
        assertTrue(result.hasErrors());

        List<SemanticError> semanticErrors = result.getErrors();
        assertEquals(2, semanticErrors.size());
        assertEquals(PROPERTIES_ALREADY_DEFINED_IN_ANCESTORS, semanticErrors.get(0).getErrorType());
        assertEquals("ApparelProduct", ((DefinitionsSemanticError) semanticErrors.get(0)).getPath());
        assertEquals(PROPERTIES_ALREADY_DEFINED_IN_ANCESTORS, semanticErrors.get(1).getErrorType());
        assertEquals("TShirt", ((DefinitionsSemanticError) semanticErrors.get(1)).getPath());
    }

    @Test
    public void definitions_semantic_validation_should_fail_when_items_is_undefined_in_array() {
        final SwaggerDeserializationResult swaggerResult = readDoc(
            "src/test/resources/semantic-validation/swagger-doc-items-property-not-defined-in-array" + ".yml");
        SemanticValidationResult result = new SemanticValidator(swaggerResult.getSwagger()).validate();

        assertTrue(result.hasErrors());
        List<SemanticError> semanticErrors = result.getErrors();
        assertEquals(1, semanticErrors.size());
        DefinitionsSemanticError definitionsSemanticError = (DefinitionsSemanticError) semanticErrors.get(0);
        assertEquals(ITEMS_PROPERTY_IS_NOT_DEFINED_IN_ARRAY, definitionsSemanticError.getErrorType());
        assertEquals("Product.categories", definitionsSemanticError.getPath());

    }

    private SwaggerDeserializationResult readDoc(String location) {
        return new Swagger20Parser().readWithInfo(location, Collections.<AuthorizationValue>emptyList());
    }
}
