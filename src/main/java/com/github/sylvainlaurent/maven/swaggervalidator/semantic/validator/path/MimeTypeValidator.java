package com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.path;

import static org.apache.commons.collections4.ListUtils.emptyIfNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.path.OperationWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.error.SemanticError;

public class MimeTypeValidator extends PathValidatorTemplate {

    @Override
    public void validate(OperationWrapper operation) {
        validateMimeTypes(operation);
        validateConsumes(operation);
        validateProduces(operation);
    }

    private void validateConsumes(OperationWrapper operation) {
        String operationName = operation.getName();
        List<String> consumes = operation.getConsumes();
        if (consumes == null) {
            consumes = new ArrayList<>(emptyIfNull(context.getSwagger().getConsumes()));
        }
        if ("get".equals(operationName) || "delete".equals(operationName) ||
                (operation.getParameters("body").isEmpty() && operation.getParameters("formData").isEmpty())) {
            if (consumes == null || !consumes.isEmpty()) {
                validationErrors
                        .add(new SemanticError(holder.getCurrentPath(), "'consumes' must be equal to 'consumes: []' "));
            }
        } else {
            if (consumes == null || consumes.isEmpty()) {
                validationErrors
                        .add(new SemanticError(holder.getCurrentPath(), "'consumes' cannot be empty"));
            }
        }
    }

    private void validateProduces(OperationWrapper operation) {
        List<String> produces = operation.getProduces();
        if (produces == null) {
            produces = new ArrayList<>(emptyIfNull(context.getSwagger().getProduces()));
        }
        boolean hasSuccessfullResponseWithSchema = operation.getResponses().stream().filter(x -> x.getName().startsWith("2"))
                .anyMatch(r -> r.getSchema() != null);
        if (hasSuccessfullResponseWithSchema) {
            if (produces == null || produces.isEmpty()) {
                validationErrors
                        .add(new SemanticError(holder.getCurrentPath(), "'produces' cannot be empty"));
            }
        } else {
            if (produces == null || !produces.isEmpty()) {
                validationErrors
                        .add(new SemanticError(holder.getCurrentPath(), "'produces' must be equal to 'produces: []'"));
            }
        }
    }

    public void validateMimeTypes(OperationWrapper operation) {
        validateMimeTypesInCollection(operation.getConsumes(), "consumes");
        validateMimeTypesInCollection(operation.getProduces(), "produces");
    }

    private void validateMimeTypesInCollection(Collection<String> collection, String message) {
        if (collection != null) {
            collection.stream().filter(x ->
                                       {
                                           try {
                                               return !(context.getMimeTypes().contains(x));
                                           } catch (Exception e) {
                                               return true;
                                           }
                                       })
                    .forEach(x -> validationErrors
                            .add(new SemanticError(holder.getCurrentPath(), "invalid MIME type '" + x + "' in '" + message + "'")));
        }
    }
}
