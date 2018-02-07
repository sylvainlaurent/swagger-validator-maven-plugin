package com.github.sylvainlaurent.maven.swaggervalidator.semantic;

import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.model.ArrayModelWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.model.ComposedModelWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.model.ModelImplWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.model.RefModelWrapper;

public interface ModelVisitor {

    void visit(ModelImplWrapper modelImplWrapper);

    void visit(RefModelWrapper refModelWrapper);

    void visit(ArrayModelWrapper arrayModelWrapper);

    void visit(ComposedModelWrapper composedModelWrapper);
}
