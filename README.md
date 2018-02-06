[![Build Status](https://travis-ci.org/sylvainlaurent/swagger-validator-maven-plugin.svg)](https://travis-ci.org/sylvainlaurent/swagger-validator-maven-plugin)

# swagger-validator-maven-plugin

This maven plugin allows to validate yaml and json files.

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
              <!-- package names where custom validators are located -->
              <customModelValidatorsPackage>com.example.validators</customValidatorsPackage>
              <customPathValidatorsPackage>com.example.validators</customPathValidatorsPackage>
            </configuration>
          </execution>
        </executions>
        <dependencies>
            <!-- dependency with custom validators -->
            <dependency>
                <groupId>com.example</groupId>
                <artifactId>custom-validators</artifactId>
                <version>${project.version}</version>
            </dependency>
        </dependencies>
      </plugin>
```

Validation failures make the build fail.

You can add your custom validators and provide plugin with them. Extend from ModelValidatorTemplate or PathValidatorTemplate 
classes for writing your validators and override necessary validation methods. See ReferenceValidator and PathValidator as examples.

Requires java 1.8.
