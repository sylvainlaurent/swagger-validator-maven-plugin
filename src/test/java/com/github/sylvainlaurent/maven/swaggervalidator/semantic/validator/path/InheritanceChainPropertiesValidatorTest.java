package com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.path;

import static com.github.sylvainlaurent.maven.swaggervalidator.SemanticValidationServiceTest.RESOURCE_FOLDER;
import static com.github.sylvainlaurent.maven.swaggervalidator.SemanticValidationServiceTest.readDoc;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import com.github.sylvainlaurent.maven.swaggervalidator.ValidatorJunitRunner;
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

}