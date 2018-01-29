package com.github.sylvainlaurent.maven.swaggervalidator;

import com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.error.DefinitionSemanticError;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.error.SemanticError;
import com.github.sylvainlaurent.maven.swaggervalidator.service.SemanticValidationService;
import io.swagger.parser.Swagger20Parser;
import io.swagger.parser.util.SwaggerDeserializationResult;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(ValidatorJunitRunner.class)
public class SemanticValidationServiceTest {

    public static final String RESOURCE_FOLDER = "src/test/resources/semantic-validation/";

    @Test
    public void operations_semantic_validation_should_fail_when_two_path_are_equal() {
        final SwaggerDeserializationResult swaggerResult = readDoc(RESOURCE_FOLDER + "equivalent-operations.yml");
        List<SemanticError> semanticErrors = new SemanticValidationService(swaggerResult.getSwagger()).validate();
        System.out.println(semanticErrors);

        assertFalse(semanticErrors.isEmpty());
        assertEquals(2, semanticErrors.size());
        assertEquals("Equivalent paths are not allowed.", semanticErrors.get(0).getMessage());
        assertEquals("Equivalent paths are not allowed.", semanticErrors.get(1).getMessage());
    }

    @Test
    public void operations_semantic_validation_should_fail_when_an_operations_contains_both_form_and_body_params() {
        final SwaggerDeserializationResult swaggerResult = readDoc(RESOURCE_FOLDER + "both-form-and-body-params.yml");
        List<SemanticError> semanticErrors = new SemanticValidationService(swaggerResult.getSwagger()).validate();
        System.out.println(semanticErrors);


        assertFalse(semanticErrors.isEmpty());
        assertEquals(1, semanticErrors.size());
        assertTrue(semanticErrors.get(0).getMessage().endsWith("Parameters cannot contain both 'body' and 'formData' types."));
    }

    @Test
    public void operations_semantic_validation_should_fail_when_operation_params_contains_multiple_body_params() {
        final SwaggerDeserializationResult swaggerResult = readDoc(RESOURCE_FOLDER + "multiple-body-params.yml");
        List<SemanticError> semanticErrors = new SemanticValidationService(swaggerResult.getSwagger()).validate();
        System.out.println(semanticErrors);


        assertFalse(semanticErrors.isEmpty());
        assertEquals(1, semanticErrors.size());
        assertTrue(semanticErrors.get(0).getMessage().endsWith("Multiple body parameters are not allowed."));
    }

    @Test
    public void operations_semantic_validation_should_fail_when_path_variables_are_not_matched_by_operation_params() {
        final SwaggerDeserializationResult swaggerResult = readDoc(RESOURCE_FOLDER + "unmatched-path-variable.yml");
        List<SemanticError> semanticErrors = new SemanticValidationService(swaggerResult.getSwagger()).validate();
        System.out.println(semanticErrors);

        assertFalse(semanticErrors.isEmpty());
        assertEquals(1, semanticErrors.size());
        assertTrue(semanticErrors.get(0).getMessage().equals("Declared path parameter 'productId' needs to be defined as a path parameter at either the path or operation level"));
    }

    @Test
    public void operations_semantic_validation_should_fail_when_operation_params_are_not_matched_by_path_variables() {
        final SwaggerDeserializationResult swaggerResult = readDoc(RESOURCE_FOLDER + "unmatched-operation-path-parameter.yml");
        List<SemanticError> semanticErrors = new SemanticValidationService(swaggerResult.getSwagger()).validate();
        System.out.println(semanticErrors);


        assertFalse(semanticErrors.isEmpty());
        assertEquals(1, semanticErrors.size());
        assertTrue(semanticErrors.get(0).getMessage().contains("Declared path parameter 'additionalPathParam' needs to be defined as a path parameter at either the path or operation level"));
    }

    @Test
    public void operations_semantic_validation_should_fail_when_operations_contains_parameters_with_same_name_and_in() {
        final SwaggerDeserializationResult swaggerResult = readDoc(RESOURCE_FOLDER + "duplicate-parameters-in-operation.yml");
        List<SemanticError> semanticErrors = new SemanticValidationService(swaggerResult.getSwagger()).validate();
        System.out.println(semanticErrors);

        assertFalse(semanticErrors.isEmpty());
        assertEquals(1, semanticErrors.size());
        assertEquals("should not have duplicate items: [product]", semanticErrors.get(0).getMessage());
    }

    @Test
    public void definitions_semantic_validation_should_fail_when_definitions_contains_undefined_reference() {
        final SwaggerDeserializationResult swaggerResult = readDoc(RESOURCE_FOLDER + "reference-not-found-in-definitions.yml");
        List<SemanticError> semanticErrors = new SemanticValidationService(swaggerResult.getSwagger()).validate();
        System.out.println(semanticErrors);


        assertFalse(semanticErrors.isEmpty());

        List<String> expectedErrorPaths = asList("Test.undefRef2", "Test.array.items.undefRef1.items");
        assertEquals(expectedErrorPaths.size(), semanticErrors.size());

        DefinitionSemanticError semanticError1 = (DefinitionSemanticError) semanticErrors.get(0);
        DefinitionSemanticError semanticError2 = (DefinitionSemanticError) semanticErrors.get(1);

        List<String> errorPaths = asList(semanticError1.getPath(), semanticError1.getPath());
        assertTrue(expectedErrorPaths.containsAll(errorPaths));

        assertTrue(semanticError1.getMessage().contains("reference"));
        assertTrue(semanticError1.getMessage().contains("cannot be found"));
        assertTrue(semanticError2.getMessage().contains("reference"));
        assertTrue(semanticError2.getMessage().contains("cannot be found"));
    }

    @Test
    public void definitions_semantic_validation_should_fail_when_model_contains_required_properties_not_defined() {
        final SwaggerDeserializationResult swaggerResult = readDoc(RESOURCE_FOLDER + "required-properties-not-defined.yml");
        List<SemanticError> semanticErrors = new SemanticValidationService(swaggerResult.getSwagger()).validate();
        System.out.println(semanticErrors);


        assertFalse(semanticErrors.isEmpty());
        assertEquals(1, semanticErrors.size());
        DefinitionSemanticError semanticError = (DefinitionSemanticError)semanticErrors.get(0);
        assertTrue(semanticError.getMessage().contains("required properties are not defined as object properties: "));
        assertEquals("Product", semanticError.getPath());
    }

    @Test
    public void definitions_semantic_validation_should_fail_when_object_property_contains_required_properties_not_defined() {

        final SwaggerDeserializationResult swaggerResult = readDoc(
            RESOURCE_FOLDER + "required-properties-not-defined-for-object-property.yml");
        List<SemanticError> semanticErrors = new SemanticValidationService(swaggerResult.getSwagger()).validate();
        System.out.println(semanticErrors);


        assertFalse(semanticErrors.isEmpty());
        assertEquals(1, semanticErrors.size());
        DefinitionSemanticError semanticError = (DefinitionSemanticError)semanticErrors.get(0);
        assertTrue(semanticError.getMessage().contains("required properties are not defined as object properties:"));
        assertEquals("Product.category.image", semanticError.getPath());
    }

    @Test
    public void definitions_semantic_validation_should_fail_when_model_contains_duplicated_required_properties() {
        final SwaggerDeserializationResult swaggerResult = readDoc(RESOURCE_FOLDER + "required-properties-duplicated.yml");
        List<SemanticError> semanticErrors = new SemanticValidationService(swaggerResult.getSwagger()).validate();
        System.out.println(semanticErrors);


        assertFalse(semanticErrors.isEmpty());
        assertEquals(1, semanticErrors.size());
        DefinitionSemanticError semanticError = (DefinitionSemanticError)semanticErrors.get(0);
        assertTrue(semanticError.getMessage().contains("required property is defined multiple times:"));
        assertEquals("Product", semanticError.getPath());
    }

    @Test
    public void definitions_semantic_validation_should_fail_when_object_property_contains_duplicated_required_properties() {

        final SwaggerDeserializationResult swaggerResult = readDoc(
            RESOURCE_FOLDER + "required-properties-duplicated-for-object-property.yml");
        List<SemanticError> semanticErrors = new SemanticValidationService(swaggerResult.getSwagger()).validate();
        System.out.println(semanticErrors);


        assertFalse(semanticErrors.isEmpty());
        assertEquals(1, semanticErrors.size());
        DefinitionSemanticError semanticError = (DefinitionSemanticError)semanticErrors.get(0);
        assertTrue(semanticError.getMessage().contains("required property is defined multiple times:"));
        assertEquals("Product.category", semanticError.getPath());
    }

    @Test
    public void definitions_semantic_validation_should_fail_when_model_contains_a_discrimininator_not_defined_as_property() {
        final SwaggerDeserializationResult swaggerResult = readDoc(
            RESOURCE_FOLDER + "discriminator-not-defined-as-property.yml");
        List<SemanticError> semanticErrors = new SemanticValidationService(swaggerResult.getSwagger()).validate();
        System.out.println(semanticErrors);


        assertFalse(semanticErrors.isEmpty());
        assertEquals(2, semanticErrors.size());

        DefinitionSemanticError error1 = (DefinitionSemanticError)semanticErrors.get(0);
        DefinitionSemanticError error2 = (DefinitionSemanticError)semanticErrors.get(1);
        assertTrue(error1.getMessage().contains("discriminator"));
        assertTrue(error1.getMessage().contains("is not a property defined at this schema"));
        assertEquals("Product", error1.getPath());
        assertTrue(error2.getMessage().contains("discriminator property"));
        assertTrue(error2.getMessage().contains("s not marked as required"));
        assertEquals("Product", error2.getPath());
    }

    @Test
    public void definitions_semantic_validation_should_fail_when_model_contains_a_discrimininator_not_defined_as_required_property() {
        final SwaggerDeserializationResult swaggerResult = readDoc(
            RESOURCE_FOLDER + "discriminator-not-defined-as-required-property.yml");
        List<SemanticError> semanticErrors = new SemanticValidationService(swaggerResult.getSwagger()).validate();
        System.out.println(semanticErrors);


        assertFalse(semanticErrors.isEmpty());
        assertEquals(1, semanticErrors.size());
        DefinitionSemanticError error = (DefinitionSemanticError)semanticErrors.get(0);
        assertTrue(error.getMessage().contains("discriminator property"));
        assertTrue(error.getMessage().contains("is not marked as required"));
        assertEquals("Product", error.getPath());
    }

    @Test
    public void definitions_semantic_validation_should_fail_when_model_contains_a_property_already_defined_in_ancestors() {
        final SwaggerDeserializationResult swaggerResult = readDoc(
            RESOURCE_FOLDER + "property-already-defined-in-ancestors.yml");
        List<SemanticError> semanticErrors = new SemanticValidationService(swaggerResult.getSwagger()).validate();
        System.out.println(semanticErrors);

        assertFalse(semanticErrors.isEmpty());

        assertEquals(2, semanticErrors.size());
        assertTrue(semanticErrors.get(0).getMessage().contains("following properties are already defined in ancestors:"));
        assertEquals("TShirt", semanticErrors.get(0).getPath());
        assertTrue(semanticErrors.get(1).getMessage().contains("following properties are already defined in ancestors:"));
        assertEquals("ApparelProduct", (semanticErrors.get(1).getPath()));
    }

    @Test
    public void definitions_semantic_validation_should_fail_when_items_is_undefined_in_array() {
        final SwaggerDeserializationResult swaggerResult = readDoc(
            RESOURCE_FOLDER + "items-property-not-defined-in-array.yml");
        List<SemanticError> semanticErrors = new SemanticValidationService(swaggerResult.getSwagger()).validate();
        System.out.println(semanticErrors);


        assertFalse(semanticErrors.isEmpty());
        assertEquals(1, semanticErrors.size());
        DefinitionSemanticError definitionSemanticError = (DefinitionSemanticError) semanticErrors.get(0);
        assertTrue(definitionSemanticError.getMessage().contains("'items' must be defined for an array"));
        assertEquals("Product.categories", definitionSemanticError.getPath());
    }

    @Test
    public void error_when_read_only_property_marked_required() {
        SwaggerDeserializationResult swaggerResult = readDoc(
                RESOURCE_FOLDER + "read-only-property-marked-required.yml");
        List<SemanticError> errors = new SemanticValidationService(swaggerResult.getSwagger()).validate();
        System.out.println(errors);

        assertFalse(errors.isEmpty());
        assertEquals(1, errors.size());
        DefinitionSemanticError error = (DefinitionSemanticError) errors.get(0);
        assertEquals("Read only properties cannot be marked as required.", error.getMessage());
        assertEquals("Test", error.getPath());
    }

    public static SwaggerDeserializationResult readDoc(String location) {
        return new Swagger20Parser().readWithInfo(location, Collections.emptyList());
    }
}
