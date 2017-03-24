package com.github.sylvainlaurent.maven.swaggervalidator;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.load.Dereferencing;
import com.github.fge.jsonschema.core.load.configuration.LoadingConfiguration;
import com.github.fge.jsonschema.core.report.ProcessingMessage;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;

import io.swagger.models.auth.AuthorizationValue;
import io.swagger.parser.Swagger20Parser;
import io.swagger.parser.SwaggerResolver;
import io.swagger.parser.util.SwaggerDeserializationResult;
import io.swagger.util.Json;
import io.swagger.util.Yaml;

public class ValidationService {

    private static final String SCHEMA_FILE = "swagger-schema.json";

    private final ObjectMapper jsonMapper = Json.mapper();
    private final ObjectMapper yamlMapper = Yaml.mapper();

    private JsonSchema schema;

    public ValidationService() {
        final InputStream is = this.getClass().getClassLoader().getResourceAsStream(SCHEMA_FILE);
        JsonNode schemaObject;
        try {
            schemaObject = jsonMapper.readTree(is);
            // using INLINE dereferencing to avoid internet access while validating
            final LoadingConfiguration loadingConfiguration = LoadingConfiguration.newBuilder()
                    .dereferencing(Dereferencing.INLINE).freeze();
            final JsonSchemaFactory factory = JsonSchemaFactory.newBuilder()
                    .setLoadingConfiguration(loadingConfiguration).freeze();
            schema = factory.getJsonSchema(schemaObject);
        } catch (IOException | ProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    public ValidationResult validate(final File file) {
        final ValidationResult validationResult = new ValidationResult();

        JsonNode spec;
        try {
            spec = readFileContent(file);
        } catch (final Exception e) {
            validationResult.addMessage("Error while parsing file " + file + ": " + e.getMessage());
            validationResult.encounteredError();
            return validationResult;
        }

        readSwaggerSpec(spec, file, validationResult);
        validateSwagger(spec, validationResult);

        return validationResult;
    }

    private void readSwaggerSpec(final JsonNode spec, File specFile, final ValidationResult validationResult) {
        // use the swagger deserializer to get human-friendly messages
        final SwaggerDeserializationResult swaggerResult = new Swagger20Parser().readWithInfo(spec);
        swaggerResult.setSwagger(
                new SwaggerResolver(swaggerResult.getSwagger(), new ArrayList<AuthorizationValue>(), specFile.getPath()).resolve());

        validationResult.addMessages(swaggerResult.getMessages());
    }

    private void validateSwagger(final JsonNode spec, final ValidationResult validationResult) {
        try {
            final ProcessingReport report = schema.validate(spec);
            if (!report.isSuccess()) {
                validationResult.encounteredError();
            }
            for (final ProcessingMessage processingMessage : report) {
                validationResult.addMessage(processingMessage.toString());
            }
        } catch (final ProcessingException e) {
            validationResult.addMessage(e.getMessage());
            validationResult.encounteredError();
        }
    }

    private JsonNode readFileContent(final File file) throws IOException {
        final String fileName = file.getName().toLowerCase();
        if (fileName.endsWith(".yml") || fileName.endsWith(".yaml")) {
            return yamlMapper.readTree(file);
        } else {
            return jsonMapper.readTree(file);
        }
    }

}
