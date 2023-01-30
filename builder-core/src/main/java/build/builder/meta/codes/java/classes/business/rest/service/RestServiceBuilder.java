package build.builder.meta.codes.java.classes.business.rest.service;

import build.builder.meta.codes.java.classes.business.rest.RestAbstractBuilder;

/**
 * Rest风格Service构建器
 *
 * @author peng_fu_lin
 * 2022-12-27 11:37
 */
public abstract class RestServiceBuilder extends RestAbstractBuilder {
    public RestServiceBuilder(){
        super.packageStatement="java.service";
    }
}