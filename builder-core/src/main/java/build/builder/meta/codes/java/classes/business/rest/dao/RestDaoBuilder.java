package build.builder.meta.codes.java.classes.business.rest.dao;

import build.builder.meta.codes.java.classes.business.rest.RestAbstractBuilder;

/**
 * Rest风格Dao构建器
 *
 * @author peng_fu_lin
 * 2023-01-30 18:10
 */
public class RestDaoBuilder extends RestAbstractBuilder {
    public RestDaoBuilder(){
        super.packageStatement="java.dao";
    }
}