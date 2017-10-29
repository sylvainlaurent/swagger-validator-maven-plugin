package com.github.sylvainlaurent.maven.swaggervalidator.semantic.error;

public class SemanticError {

    private final ErrorType errorType;
    private final String message;

    public String getMessage() {
        return message;
    }

    public ErrorType getErrorType() {
        return errorType;
    }

    public enum ErrorType {
        PROPERTIES_ALREADY_DEFINED_IN_ANCESTORS,
        DISCRIMINATOR_NOT_DEFINED_AS_REQUIRED_PROPERTY,
        DISCRIMINATOR_NOT_DEFINED_AS_PROPERTY,
        OPERATION_CONTAINS_BOTH_FORM_AND_BODY_PARAMETER,
        API_PATH_IS_NOT_UNIQUE,
        PATH_PARAMS_DONT_MATCH_DEFINED_OPERATION_PARAMS,
        REQUIRED_PROPERTIES_NOT_DEFINED_AS_OBJECT_PROPERTIES,
        REQUIRED_PROPERTIES_ARE_DUPLICATED,
        OPERATION_CONTAINS_DUPLICATE_PARAMETERS,
        OPERATION_CONTAINS_MULTIPLE_BODY_PARAMETERS,
        REFERENCE_DOESNT_POINT_TO_AN_EXISTING_DEFINITION,
        ITEMS_PROPERTY_IS_NOT_DEFINED_IN_ARRAY
    }

    SemanticError(ErrorType errorType, String message) {
        this.errorType = errorType;
        this.message = message;
    }

    @Override
    public String toString() {
        return "SemanticError{" + "errorType=" + errorType + ", message='" + message + '\'' + '}';
    }

}
