package build.builder.meta.codes.java.classes.business.rest.service;

import build.builder.data.classes.enums.MethodType;
import build.builder.data.classes.meta.MethodMeta;
import build.builder.meta.codes.java.classes.business.rest.RestBusinessBuilder;

/**
 * Rest风格Service构建器
 *
 * @author peng_fu_lin
 * 2022-12-27 11:37
 */
public abstract class RestServiceBuilder extends RestBusinessBuilder {

    public RestServiceBuilder(){
        super.packageStatement="java.service";
    }

    @Override
    protected MethodMeta doGetDefaultMethod(String methodName) {
        MethodMeta methodMeta = super.doGetDefaultMethod(methodName);
        methodMeta.setMethodType(MethodType.ABSTRACT);
        return methodMeta;
    }

}