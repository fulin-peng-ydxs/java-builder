package build.builder.data.classes.meta;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;

/**参数元信息
 *
 * 2022/9/7 0007-13:42
 * @author pengfulin
*/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParamMeta {
    /**参数名称*/
    private String paramName;
    /**参数类型*/
    private Class<?> paramType;
    /**参数泛型*/
    private List<GenericMeta> genericParams;
    /**参数注解*/
    private Map<Class<? extends Annotation>,AnnotationMeta> paramAnnotations;
    /**业务类型：用于传递构建中的类型*/
    private BusinessClass businessClass;
}
