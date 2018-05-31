package com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.path;

import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.VisitableProperty;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.path.OperationWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.path.ResponseWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.property.ArrayPropertyWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.ValidationContext;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.error.SchemaError;

import io.swagger.models.properties.Property;

public class ResponseValidator extends PathValidatorTemplate {

    @Override
    public void setValidationContext(ValidationContext context) {
        this.context = context;
    }

    @Override
    public void validate(OperationWrapper operation) {
        if (operation.getResponses() == null || operation.getResponses().isEmpty()) {
            validationErrors.add(new SchemaError(holder.getCurrentPath(), "missingProperty: 'responses'"));
        }
    }

    @Override
    public void validate(ResponseWrapper response) {
        if (response == null) {
            validationErrors.add(new SchemaError(holder.getCurrentPath(), "should be an object"));
            return;
        }
        if (response.getDescription() == null) {
            validationErrors.add(new SchemaError(holder.getCurrentPath(), "missingProperty: 'description'"));
            return;
        }
        VisitableProperty<? extends Property> schema = response.getSchema();
        if (schema != null) {
            holder.push("schema");
            validateSchema(schema);
            holder.pop();
        }
    }

    private void validateSchema(VisitableProperty<? extends Property> schema) {
        if (schema.getType().equals("array")) {
            if (((ArrayPropertyWrapper) schema).getProperty().getItems() == null) {
                validationErrors.add(
                        new SchemaError(holder.getCurrentPath(), "'type: array', require a sibling 'items:' field"));
            }
        }
    }
}
