package com.github.sylvainlaurent.maven.swaggervalidator.instrumentation;

import com.github.sylvainlaurent.maven.swaggervalidator.semantic.node.ObjectPropertyRequiredStore;
import net.bytebuddy.implementation.bind.annotation.Argument;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import net.bytebuddy.implementation.bind.annotation.This;

import java.util.List;
import java.util.concurrent.Callable;

public final class ObjectPropertyRequiredInterceptor {

    private ObjectPropertyRequiredInterceptor() {
        // EMPTY
    }

    @RuntimeType
    public static Object setRequiredProperties(@This Object object, @SuperCall Callable<?> zuper, @Argument(value = 0) List<String> requiredProperties) throws Exception {
        ObjectPropertyRequiredStore.storeRequiredProperties(object, requiredProperties);
        return zuper.call();
    }

}
