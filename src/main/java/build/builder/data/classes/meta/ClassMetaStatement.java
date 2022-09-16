package build.builder.data.classes.meta;
import build.builder.data.classes.enums.CommentType;
import build.builder.data.classes.enums.PermissionType;
import lombok.Getter;
import lombok.Setter;
import java.lang.annotation.Annotation;
import java.util.Map;
/**
 * 类元信息声明
 *
 * @author peng_fu_lin
 * 2022-09-09 14:10
 */
@Getter
@Setter
public class ClassMetaStatement {
    /**类权限*/
    private PermissionType classPermission=PermissionType.PUBLIC;
    /**类名*/
    private String className;
    /**类注解*/
    private Map<Class<? extends Annotation>, AnnotationMeta> classAnnotations;
    /**继承类型*/
    private ExtendsMeta classExtends;
    /**实现类型*/
    private Map<Class<?>, ImplementsMeta> classImplements;
    /**类注释*/
    private CommentMeta classComment =new CommentMeta(CommentType.Many,null, null);;
}