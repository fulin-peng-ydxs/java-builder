package build.builder.meta.codes.java.classes.business.rest;

import build.builder.data.classes.enums.MethodType;
import build.builder.data.classes.meta.MethodMeta;

/**
 * Rest抽象业务构建器
 *
 * @author peng_fu_lin
 * 2023-01-30 18:11
 */
public class RestAbstractBuilder extends RestBusinessBuilder{

    @Override
    protected MethodMeta doGetDefaultMethod(String methodName) {
        MethodMeta methodMeta = super.doGetDefaultMethod(methodName);
        methodMeta.setMethodType(MethodType.ABSTRACT);
        return methodMeta;
    }
}