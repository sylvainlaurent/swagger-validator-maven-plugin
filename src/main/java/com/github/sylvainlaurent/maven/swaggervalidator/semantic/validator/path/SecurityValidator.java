package com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.path;

import com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.error.SemanticError;
import io.swagger.models.auth.OAuth2Definition;
import io.swagger.models.auth.SecuritySchemeDefinition;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SecurityValidator extends PathValidatorTemplate {

    private List<SemanticError> validationErrors = new ArrayList<>();

    @Override
    public List<SemanticError> getErrors() {
        return validationErrors;
    }

    @Override
    public void validate(String securityDefinition, List<String> scopes) {
        Map<String, SecuritySchemeDefinition> securityDefinitions = context.getSecurityDefinitions();
        SecuritySchemeDefinition schemeDefinition = securityDefinitions.get(securityDefinition);

        if (schemeDefinition == null) {
            validationErrors.add(new SemanticError(holder.getCurrentPath(),
                    "Security requirements must match a security definition"));
            return;
        }

        if (schemeDefinition.getType().equals("oauth2")) {
            Map<String, String> definitionScopes = ((OAuth2Definition) schemeDefinition).getScopes();
            for (String scope : scopes) {
                if (definitionScopes.get(scope) == null) {
                    validationErrors.add(new SemanticError(holder.getCurrentPath(),
                            "Security scope definition '" + scope + "' could not be resolved"));
                }
            }
        }
    }
}
