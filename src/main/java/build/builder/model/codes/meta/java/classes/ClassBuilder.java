package build.builder.model.codes.meta.java.classes;
import build.builder.data.classes.meta.ClassMetaStatement;
import build.builder.data.classes.model.ClassModel;
import build.builder.model.codes.meta.java.JavaCodeBuilder;
import java.util.Set;
/**
 * 类构建器
 *
 * @author peng_fu_lin
 * 2022-09-06 17:55
 */
public abstract class ClassBuilder extends JavaCodeBuilder<ClassModel> {

    @Override
    protected String convertCode(ClassModel model) {
        StringBuilder builder = new StringBuilder();
        builder.append(toClassPackageStatement(model))
                .append( modelClearanceLineStyle());
        String classImports = toClassImports(model);
        if(classImports!=null){
            builder.append(classImports)
                    .append( modelClearanceLineStyle());
        }
        builder.append(toClassStatement(model))
                .append( modelClearanceLineStyle());
        String classAttributes = toClassAttributes(model);
        if(classAttributes!=null){
            builder.append(classAttributes)
                    .append( modelClearanceLineStyle());
        }
        String classMethods = toClassMethods(model);
        if(classMethods!=null){
            builder.append(classMethods)
                    .append(modelClearanceLineStyle());
        }
        return builder.toString();
    }


    /**生成类包声明
     * 2022/9/8 0008-15:29
     * @author pengfulin
     */
    protected String toClassPackageStatement(ClassModel model){
        return doGetPackageStatement(model.getClassPackage());
    }

    /**生成类导入
     * 2022/9/8 0008-15:39
     * @author pengfulin
     */
    protected String toClassImports(ClassModel model){
        Set<Class<?>> classImports = model.getClassImports();
        return doGetClassImports(classImports);
    }

    /**生成类声明区
     * 2022/9/8 0008-15:30
     * @author pengfulin
     */
    protected String toClassStatement(ClassModel classModel){
        ClassMetaStatement classMetaStatement = classModel.getClassMetaStatement();
        return doGetClassMetaStatement(classMetaStatement);
    }

    /**生成类属性
     * 2022/9/8 0008-15:48
     * @author pengfulin
     */
    protected String toClassAttributes(ClassModel classModel){
        return doGetAttributes(classModel.getAttributes());
    }

    /**生成类方法
     * 2022/9/8 0008-15:50
     * @author pengfulin
     */
    protected String toClassMethods(ClassModel classModel){
        return doGetMethods(classModel.getMethods());
    }
}