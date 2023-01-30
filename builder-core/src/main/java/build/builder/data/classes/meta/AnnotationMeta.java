package build.builder.data.classes.meta;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.lang.annotation.Annotation;
import java.util.Map;

/**注解元信息
 * 2022/9/7 0007-11:47
 * @author pengfulin
*/
@Getter
@Setter
@AllArgsConstructor
public class AnnotationMeta  {
    /**注解类型*/
    private Class<? extends Annotation> annotationClass;
    /**注解参数*/
    private Map<String,Object> annotationValues;
}
