package build.builder.meta.codes.java.classes.bean;

import build.builder.data.classes.meta.AnnotationMeta;
import build.builder.data.classes.meta.ClassMetaStatement;
import build.source.data.meta.bean.BuildBean;
import lombok.Data;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
/**
 * 使用Lombok插件风格的BeanBuilder
 *
 * @author peng_fu_lin
 * 2022-09-21 15:25
 */
public class LombokBeanBuilder extends BeanBuilder{

    /**类声明加上对应注解
     * 2022/9/21 0021-15:29
     * @author pengfulin
    */
    @Override
    protected ClassMetaStatement getClassMetaStatement(BuildBean buildBean) {
        ClassMetaStatement classMetaStatement = super.getClassMetaStatement(buildBean);
        Map<Class<? extends Annotation>, AnnotationMeta> classAnnotations = classMetaStatement.getClassAnnotations();
        if (classAnnotations==null)
            classAnnotations=new HashMap<>();
        classAnnotations.put(Data.class,new AnnotationMeta(Data.class,null));
        classMetaStatement.setClassAnnotations(classAnnotations);
        return classMetaStatement;
    }
}