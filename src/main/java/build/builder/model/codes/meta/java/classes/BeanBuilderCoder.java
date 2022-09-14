package build.builder.model.codes.meta.java.classes;

import build.source.data.meta.bean.BuildBean;

/**
 * @author peng_fu_lin
 * 2022-09-09 10:26
 */
public abstract class BeanBuilderCoder extends ClassBuilder{

    public Class<?> supportedBuildSource() {return BuildBean.class;}
}