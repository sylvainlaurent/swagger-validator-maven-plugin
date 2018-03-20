package com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.path;

import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.PathObject;
import io.swagger.models.Scheme;

public class SchemeWrapper implements PathObject {

    private Scheme scheme;

    public SchemeWrapper(Scheme scheme) {
        this.scheme = scheme;
    }

    public Scheme getScheme() {
        return scheme;
    }

    @Override
    public String getName() {
        return  "scheme";
    }
}
