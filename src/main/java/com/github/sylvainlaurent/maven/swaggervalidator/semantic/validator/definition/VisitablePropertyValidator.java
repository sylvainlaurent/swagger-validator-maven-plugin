package com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.definition;

import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.property.ArrayPropertyWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.property.ObjectPropertyWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.property.RefPropertyWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.property.UnhandledPropertyWrapper;

public interface VisitablePropertyValidator {

    default void validate(ObjectPropertyWrapper objectProperty) {
    }

    default void validate(ArrayPropertyWrapper arrayProperty) {
    }

    default void validate(RefPropertyWrapper refPropertyWrapper) {
    }

    default void validate(UnhandledPropertyWrapper refPropertyWrapper) {
    }
}
