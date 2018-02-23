package com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.error;

public class DefinitionSemanticError extends SemanticError {

    public DefinitionSemanticError(String path, String message) {
        super(path, message);
    }

    @Override
    public String toString() {
        return "DefinitionSemanticError at " + path + ": " + getMessage();
    }
}
