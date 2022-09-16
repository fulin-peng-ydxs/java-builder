package build.builder.data.classes.meta;


import build.builder.data.classes.enums.MethodType;
import build.builder.data.classes.enums.PermissionType;
import lombok.Getter;
import lombok.Setter;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;

/**方法元信息
 *
 * 2022/9/7 0007-13:42
 * @author pengfulin
*/
@Getter
@Setter
public class MethodMeta {
    /**方法名称*/
    private String methodName;
    /**方法返回值*/
    private Class<?> methodReturn;
    /**方法参数*/
    private List<ParamMeta> methodParams;
    /**方法权限*/
    private PermissionType methodPermission=PermissionType.PUBLIC;
    /**方法类型*/
    private MethodType methodType=MethodType.BASIS;
    /**方法内容*/
    private String methodContent;
    /**方法注释*/
    private CommentMeta methodComment;
    /**方法注解*/
    private Map<Class<? extends Annotation>,AnnotationMeta> methodAnnotations;
}