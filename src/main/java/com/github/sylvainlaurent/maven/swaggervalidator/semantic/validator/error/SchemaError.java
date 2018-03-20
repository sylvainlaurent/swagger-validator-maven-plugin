package com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.error;

public class SchemaError extends SemanticError {

    public SchemaError(String path, String message) {
        super(path, message);
    }

    @Override
    public String toString() {
        return "SchemaError at " + path + ": " + getMessage();
    }

}
