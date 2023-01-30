package build.builder.meta.codes.java.classes.business.rest.service;


import build.builder.meta.codes.java.classes.business.rest.RestBusinessBuilder;

/**
 * Rest风格Service实现构建器
 *
 * @author peng_fu_lin
 * 2022-12-27 15:51
 */
public class RestServiceImplBuilder extends RestBusinessBuilder {

    public RestServiceImplBuilder(){
       super.packageStatement="java.service.impl";
    }
}