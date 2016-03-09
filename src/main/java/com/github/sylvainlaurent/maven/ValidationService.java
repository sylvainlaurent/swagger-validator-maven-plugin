package com.github.sylvainlaurent.maven;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
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

	private ObjectMapper jsonMapper = Json.mapper();
	private ObjectMapper yamlMapper = Yaml.mapper();

	private JsonSchema schema;

	public ValidationService() {
		InputStream is = this.getClass().getClassLoader().getResourceAsStream(SCHEMA_FILE);
		JsonNode schemaObject;
		try {
			schemaObject = jsonMapper.readTree(is);
			JsonSchemaFactory factory = JsonSchemaFactory.byDefault();
			schema = factory.getJsonSchema(schemaObject);
		} catch (IOException | ProcessingException e) {
			throw new RuntimeException(e);
		}

	}

	public ValidationResult validate(File file) {
		ValidationResult validationResult = new ValidationResult();

		JsonNode spec;
		try {
			spec = readFileContent(file);
		} catch (Exception e) {
			validationResult.addMessage("Error while parsing file " + file + ": " + e.getMessage());
			validationResult.encounteredError();
			return validationResult;
		}

		readSwaggerSpec(spec, validationResult);
		validateSwagger(spec, validationResult);

		return validationResult;
	}

	private void readSwaggerSpec(JsonNode spec, ValidationResult validationResult) {
		// use the swagger deserializer to get human-friendly messages
		SwaggerDeserializationResult swaggerResult = new Swagger20Parser().readWithInfo(spec);
		swaggerResult.setSwagger(
				new SwaggerResolver(swaggerResult.getSwagger(), new ArrayList<AuthorizationValue>(), null).resolve());

		validationResult.addMessages(swaggerResult.getMessages());
	}

	private void validateSwagger(JsonNode spec, ValidationResult validationResult) {
		try {
			ProcessingReport report = schema.validate(spec);
			if (!report.isSuccess()) {
				validationResult.encounteredError();
			}
			for (ProcessingMessage processingMessage : report) {
				validationResult.addMessage(processingMessage.getMessage());
			}
		} catch (ProcessingException e) {
			validationResult.addMessage(e.getMessage());
			validationResult.encounteredError();
		}
	}

	private JsonNode readFileContent(File file) throws IOException {
		String fileName = file.getName().toLowerCase();
		if (fileName.endsWith(".yml") || fileName.endsWith(".yaml")) {
			return yamlMapper.readTree(file);
		} else {
			return jsonMapper.readTree(file);
		}
	}

}
