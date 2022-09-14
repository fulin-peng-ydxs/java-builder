package build.builder.data.classes.meta;

import build.builder.data.classes.enums.FieldType;
import build.builder.data.classes.enums.PermissionType;
import lombok.Getter;
import lombok.Setter;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
/**字段元信息
 *
 * 2022/9/7 0007-13:43
 * @author pengfulin
*/
@Getter
@Setter
public class FieldMeta {
    /**字段名称*/
    private String fieldName;
    /**字段类型*/
    private FieldType fieldType;
    /**字段值*/
    private String fieldValue;
    /**字段权限*/
    private PermissionType fieldPermission;
    /**字段注解*/
    private Map<Class<? extends Annotation>, AnnotationMeta> fieldAnnotations;
    /**字段注释*/
    private CommentMeta fieldComment;
    /**字段泛型*/
    private List<Class<?>> genericParams;
    /**是否静态*/
    private boolean isStatic;
    /**是否包装*/
    private boolean isPackaging;
}
