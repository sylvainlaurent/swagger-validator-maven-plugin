package com.github.sylvainlaurent.maven.swaggervalidator.semantic;

import static com.google.common.collect.Collections2.transform;
import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import com.github.sylvainlaurent.maven.swaggervalidator.semantic.error.SemanticError;
import com.google.common.base.Function;

public class SemanticValidationResult {

    private final List<SemanticError> errors;

    public SemanticValidationResult(List<SemanticError> errors) {
        this.errors = errors;
    }

    public List<String> messages() {
        return newArrayList(transform(errors, new Function<SemanticError, String>() {
            @Override
            public String apply(SemanticError error) {
                return error.getMessage();
            }
        }));
    }

    public List<SemanticError> getErrors() {
        System.out.println(messages());
        return errors;
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }
}
