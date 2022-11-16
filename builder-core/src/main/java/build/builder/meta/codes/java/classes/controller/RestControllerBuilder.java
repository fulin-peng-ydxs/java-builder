package build.builder.meta.codes.java.classes.controller;

import build.builder.data.classes.meta.MethodMeta;
import build.source.data.meta.business.controller.ControllerBean;
import java.util.Map;
import java.util.Set;

/**
 * Rest风格的Controller构建器
 *
 * @author peng_fu_lin
 * 2022-11-16 11:12
 */
public class RestControllerBuilder extends ControllerBuilder{

    @Override
    protected Map<String, MethodMeta> getQueryInterface(ControllerBean controllerBean, Set<Class<?>> importClasses) {
        return null;
    }

    @Override
    protected Map<String, MethodMeta> getDeleteInterface(ControllerBean controllerBean, Set<Class<?>> importClasses) {
        return null;
    }

    @Override
    protected Map<String, MethodMeta> getUpdateInterface(ControllerBean controllerBean, Set<Class<?>> importClasses) {
        return null;
    }

    @Override
    protected Map<String, MethodMeta> getAddInterface(ControllerBean controllerBean, Set<Class<?>> importClasses) {
        return null;
    }
}