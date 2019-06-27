package com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.path;

import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.path.OperationWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.MediaType;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.error.SemanticError;

import java.util.Collection;

import static org.apache.commons.collections4.IterableUtils.emptyIfNull;

public class MediaTypeValidator extends PathValidatorTemplate {

    @Override
    public void validate(OperationWrapper operation) {
        validateMediaTypes(operation);
    }

    public void validateMediaTypes(OperationWrapper operation) {
        validateMediaTypesInCollection(operation.getConsumes(), "consumes");
        validateMediaTypesInCollection(operation.getProduces(), "produces");
    }

    private void validateMediaTypesInCollection(Collection<String> collection, String message) {
        emptyIfNull(collection).forEach(mediaType -> {
            if (!MediaType.isValid(mediaType)) {
                validationErrors.add(new SemanticError(holder.getCurrentPath(), "invalid media type '" + mediaType + "' in '" + message + "'"));
            }
        });

    }
}
