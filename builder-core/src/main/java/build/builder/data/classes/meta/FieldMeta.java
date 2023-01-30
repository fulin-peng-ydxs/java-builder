package build.builder.data.classes.meta;

import build.builder.data.classes.enums.CommentType;
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
    private PermissionType fieldPermission=PermissionType.PRIVATE;
    /**字段注解*/
    private Map<Class<? extends Annotation>, AnnotationMeta> fieldAnnotations;
    /**字段注释*/
    private CommentMeta fieldComment= new CommentMeta(CommentType.ONE,
          null, null);
    /**字段泛型*/
    private List<GenericMeta> genericParams;
    /**是否静态*/
    private boolean isStatic;
    /**是否包装：用于判定基本数据类型是否使用包装类*/
    private boolean isPackaging=true;

    /**业务类型：用于传递构建中的类型*/
    private BusinessClass businessClass;
}
