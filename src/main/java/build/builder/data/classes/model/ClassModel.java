package build.builder.data.classes.model;


import build.builder.data.BuildModel;
import build.builder.data.classes.meta.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Map;
import java.util.Set;
/**类构建模型
 *
 * 2022/9/7 0007-13:47
 * @author pengfulin
*/
@Getter
@Setter
public class ClassModel extends BuildModel {
    //包声明
    protected String classPackage;
    //类导入
    protected Set<Class<?>> classImports;
    //类声明
    protected ClassMetaStatement classMetaStatement;
    //所含属性
    protected Map<String, FieldMeta> attributes;
    //所含方法
    protected Map<String, MethodMeta> methods;
}
