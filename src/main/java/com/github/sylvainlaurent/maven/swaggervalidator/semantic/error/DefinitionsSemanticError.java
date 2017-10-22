package com.github.sylvainlaurent.maven.swaggervalidator.semantic.error;

public class DefinitionsSemanticError extends SemanticError {

    private final String path;

    DefinitionsSemanticError(ErrorType errorType, String path, String message) {
        super(errorType, message);
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    @Override
    public String toString() {
        return "DefinitionsSemanticError{" + super.toString() + "," + "path='" + path + '\'' + '}';
    }
}
