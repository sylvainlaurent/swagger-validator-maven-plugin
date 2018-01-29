[![Build Status](https://travis-ci.org/sylvainlaurent/swagger-validator-maven-plugin.svg)](https://travis-ci.org/sylvainlaurent/swagger-validator-maven-plugin)

# swagger-validator-maven-plugin

This maven plugin allows to validate yaml and json files.
The validation process is the same as [validator-badge](https://github.com/swagger-api/validator-badge).

## Plugin configuration

```xml
      <plugin>
        <groupId>com.github.sylvainlaurent.maven</groupId>
        <artifactId>swagger-validator-maven-plugin</artifactId>
        <version>...</version>
        <executions>
          <execution>
            <id>validate</id>
            <phase>validate</phase>
            <goals>
              <goal>validate</goal>
            </goals>
            <configuration>
              <includes>
                <include>src/main/resources/swagger-*.yml</include>
                <include>src/main/resources/swagger-*.json</include>
                <!-- other <include> may be added -->
              </includes>
              <excludes>
                <exclude>src/main/resources/swagger-do-not-validate*.yml</exclude>
                <!-- <exclude> is optional, others may be added -->
              </excludes>
              <customModelValidatorsPackage>com.example.validators</customValidatorsPackage>
              <customPathValidatorsPackage>com.example.validators</customPathValidatorsPackage>
            </configuration>
          </execution>
        </executions>
        <dependencies>
            <dependency>
                <groupId>com.example</groupId>
                <artifactId>custom-validators</artifactId>
                <version>${project.version}</version>
            </dependency>
        </dependencies>
      </plugin>
```

Validation failures make the build fail.

You can add your custom validators and provide plugin with them. Extend ModelValidatorTemplate or PathValidatorTemplate 
classes for writing your validators, override necessary validation methods.

Requires java 1.8.
