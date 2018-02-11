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
import static org.junit.Assert.*;

@RunWith(ValidatorJunitRunner.class)
public class SecurityValidatorTest {

    private static Logger logger = LoggerFactory.getLogger(SecurityValidatorTest.class);

    @Test
    public void should_succeed_when_security_is_valid() {
        SwaggerDeserializationResult swaggerResult = readDoc(
                RESOURCE_FOLDER + "security-definitions-valid.yml");
        List<SemanticError> errors = new SemanticValidationService(swaggerResult.getSwagger()).validate();
        logger.info(errors.toString());

        assertTrue(errors.isEmpty());
    }

    @Test
    public void fail_when_security_scope_definition_cannot_be_resolved() {
        SwaggerDeserializationResult swaggerResult = readDoc(
                RESOURCE_FOLDER + "security-scope-definition-cannot-be-resolved.yml");
        List<SemanticError> errors = new SemanticValidationService(swaggerResult.getSwagger()).validate();
        logger.info(errors.toString());

        assertFalse(errors.isEmpty());
        assertEquals(2, errors.size());
        SemanticError error1 = errors.get(0);
        assertEquals("Security scope definition 'read:category' could not be resolved",
                error1.getMessage());
        assertEquals("paths./category/{id}.get.security", error1.getPath());
        SemanticError error2 = errors.get(1);
        assertEquals("Security scope definition 'write:category' could not be resolved",
                error2.getMessage());
        assertEquals("paths./category/{id}.get.security", error2.getPath());
    }

    @Test
    public void fail_when_used_security_definition_is_absent() {
        SwaggerDeserializationResult swaggerResult = readDoc(
                RESOURCE_FOLDER + "used-security-definition-absent.yml");
        List<SemanticError> errors = new SemanticValidationService(swaggerResult.getSwagger()).validate();
        logger.info(errors.toString());

        assertFalse(errors.isEmpty());
        assertEquals(1, errors.size());
        SemanticError error = errors.get(0);
        assertEquals("Security requirements must match a security definition",
                error.getMessage());
        assertEquals("paths./category/{id}.get.security", error.getPath());
    }
}