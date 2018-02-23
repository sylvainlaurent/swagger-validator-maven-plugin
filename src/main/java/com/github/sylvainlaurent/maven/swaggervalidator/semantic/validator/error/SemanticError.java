package com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.error;

import org.apache.commons.lang3.StringUtils;

public class SemanticError {

    private String message;
    protected String path;

    public String getMessage() {
        return message;
    }

    public SemanticError(String message) {
        this.message = message;
    }

    public SemanticError(String path, String message) {
        this.path = path;
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    @Override
    public String toString() {
        if (StringUtils.isEmpty(path)) {
            return "SemanticError: " + message;

        }
        return "SemanticError at " + path + ": " + message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        SemanticError that = (SemanticError) o;

        return toString().equals(that.toString());
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}
