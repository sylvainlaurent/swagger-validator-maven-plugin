package com.github.sylvainlaurent.maven.swaggervalidator.util;

import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.definition.VisitableModelValidator;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.path.SwaggerPathValidator;
import com.github.sylvainlaurent.maven.swaggervalidator.util.validators.model.ModelValidatorImpl;
import com.github.sylvainlaurent.maven.swaggervalidator.util.validators.model.ModelValidatorTemplateImpl;
import com.github.sylvainlaurent.maven.swaggervalidator.util.validators.path.PathValidatorImpl;
import com.github.sylvainlaurent.maven.swaggervalidator.util.validators.path.PathValidatorTemplateImpl;
import com.github.sylvainlaurent.maven.swaggervalidator.util.validators.path.SwaggerPathValidatorImpl;

public class UtilTest {

    private static final String MODEL_VALIDATORS_PACKAGE = "com.github.sylvainlaurent.maven.swaggervalidator.util.validators.model";
    private static final String PATH_VALIDATORS_PACKAGE = "com.github.sylvainlaurent.maven.swaggervalidator.util.validators.path";

    private static final Class<VisitableModelValidator> MODEL_VALIDATOR_INTERFACE = VisitableModelValidator.class;
    private static final Class<SwaggerPathValidator> PATH_VALIDATOR_INTERFACE = SwaggerPathValidator.class;

    @Test
    public void createModelValidatorInstances() throws Exception {
        Set<VisitableModelValidator> instances = Util.createInstances(MODEL_VALIDATORS_PACKAGE,
                MODEL_VALIDATOR_INTERFACE);

        Assert.assertTrue(instances.stream().anyMatch(p -> p.getClass().equals(ModelValidatorImpl.class)));
        Assert.assertTrue(instances.stream().anyMatch(p -> p.getClass().equals(ModelValidatorTemplateImpl.class)));

    }

    @Test
    public void createPathValidatorInstances() throws Exception {
        Set<SwaggerPathValidator> instances = Util.createInstances(PATH_VALIDATORS_PACKAGE, PATH_VALIDATOR_INTERFACE);

        Assert.assertTrue(instances.stream().anyMatch(p -> p.getClass().equals(PathValidatorImpl.class)));
        Assert.assertTrue(instances.stream().anyMatch(p -> p.getClass().equals(PathValidatorTemplateImpl.class)));
        Assert.assertTrue(instances.stream().anyMatch(p -> p.getClass().equals(SwaggerPathValidatorImpl.class)));
    }

}