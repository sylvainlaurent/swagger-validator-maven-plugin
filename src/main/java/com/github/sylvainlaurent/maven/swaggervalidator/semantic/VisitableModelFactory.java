package com.github.sylvainlaurent.maven.swaggervalidator.semantic;

import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.VisitableModel;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.model.ArrayModelWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.model.ComposedModelWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.model.ModelImplWrapper;
import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.model.RefModelWrapper;
import io.swagger.models.ArrayModel;
import io.swagger.models.ComposedModel;
import io.swagger.models.Model;
import io.swagger.models.ModelImpl;
import io.swagger.models.RefModel;

public class VisitableModelFactory {

    private VisitableModelFactory() {
        // private constructor
    }

    public static VisitableModel createVisitableModel(String modelName, Model model) {
        if (model instanceof ModelImpl) {
            return new ModelImplWrapper(modelName, (ModelImpl) model);
        }
        if (model instanceof ArrayModel) {
            return new ArrayModelWrapper(modelName, (ArrayModel) model);
        }
        if (model instanceof ComposedModel) {
            return new ComposedModelWrapper(modelName, (ComposedModel) model);
        }
        if (model instanceof RefModel) {
            return new RefModelWrapper(modelName, (RefModel) model);
        }

        throw new IllegalArgumentException("Model " + modelName + ": " + model.getClass() + " + type not supported");
    }

}
