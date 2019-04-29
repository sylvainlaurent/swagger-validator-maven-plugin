package com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.path;

import static com.github.sylvainlaurent.maven.swaggervalidator.SemanticValidationServiceTest.RESOURCE_FOLDER;
import static com.github.sylvainlaurent.maven.swaggervalidator.SemanticValidationServiceTest.readDoc;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import com.github.sylvainlaurent.maven.swaggervalidator.ValidatorJunitRunner;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.error.DefinitionSemanticError;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.error.SemanticError;
import com.github.sylvainlaurent.maven.swaggervalidator.service.SemanticValidationService;
import io.swagger.parser.util.SwaggerDeserializationResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(ValidatorJunitRunner.class)
public class InheritanceChainPropertiesValidatorTest {

    private static Logger logger = LoggerFactory.getLogger(InheritanceChainPropertiesValidatorTest.class);

    @Test
    public void succeed_when_ancestor_has_no_properties() {
        SwaggerDeserializationResult swaggerResult = readDoc(
                RESOURCE_FOLDER + "inheritance-hierarchy-model-valid.yml");
        List<SemanticError> errors = new SemanticValidationService(swaggerResult.getSwagger()).validate();
        logger.info(errors.toString());

        assertTrue(errors.isEmpty());
    }

    @Test
    public void fail_when_hierarchy_has_cyclic_refs() {
        SwaggerDeserializationResult swaggerResult = readDoc(
                RESOURCE_FOLDER + "inheritance-hierarchy-model-with-cycle-ref-models-invalid.yml");
        List<SemanticError> errors = new SemanticValidationService(swaggerResult.getSwagger()).validate();
        logger.info(errors.toString());
        assertFalse(errors.isEmpty());
        assertTrue(errors.contains(new DefinitionSemanticError("TypedProduct1","cyclic reference 'CatalogProduct'.")));
        assertTrue(errors.contains(new DefinitionSemanticError("Product","required property is defined multiple times: [productType]")));
        assertTrue(errors.contains(new DefinitionSemanticError("Product","following properties are already defined in ancestors: [description, productType]")));
    }
}