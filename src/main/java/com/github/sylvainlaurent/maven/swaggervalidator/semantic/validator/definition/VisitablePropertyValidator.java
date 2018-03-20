package com.github.sylvainlaurent.maven.swaggervalidator.semantic.validator.definition;

import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.property.ArrayPropertyWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.property.ObjectPropertyWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.property.RefPropertyWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.property.UnhandledPropertyWrapper;

public interface VisitablePropertyValidator {

    void validate(ObjectPropertyWrapper objectProperty);

    void validate(ArrayPropertyWrapper arrayProperty);

    void validate(RefPropertyWrapper refPropertyWrapper);

    void validate(UnhandledPropertyWrapper refPropertyWrapper);
}
