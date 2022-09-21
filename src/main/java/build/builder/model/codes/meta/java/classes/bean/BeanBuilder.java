package build.builder.model.codes.meta.java.classes.bean;

import build.builder.data.classes.enums.ClassStructure;
import build.builder.data.classes.enums.FieldType;
import build.builder.data.classes.meta.*;
import build.builder.data.classes.model.ClassModel;
import build.builder.model.codes.meta.java.classes.ClassBuilder;
import build.builder.util.ClassUtil;
import build.builder.util.StringUtil;
import build.source.data.meta.bean.BuildBean;
import build.source.data.meta.bean.BuildBeanItem;
import java.util.*;
/**Bean构建器
 * @author peng_fu_lin
 * 2022-09-09 10:26
 */
public abstract class BeanBuilder extends ClassBuilder {

    public BeanBuilder(){
        super.packageStatement="java.beans";
    }

    @Override
    public Class<?> supportedBuildSource() {return BuildBean.class;}

    @Override
    protected ClassModel getBuildModel(Object buildDataModel) {
        if(buildDataModel instanceof BuildBean)
            return doGetBeanModel((BuildBean) buildDataModel);
        return null;
    }

    /**生成Bean模型
     * 2022/9/15 0015-10:27
     * @author pengfulin
    */
    protected ClassModel doGetBeanModel(BuildBean buildBean){
        ClassModel classModel = new ClassModel();
        //包声明
        classModel.setClassPackage(packageStatement);
        //类声明
        ClassMetaStatement classMetaStatement = getClassMetaStatement(buildBean);
        classModel.setClassMetaStatement(classMetaStatement);
        //属性
        Map<String, FieldMeta> attributes = getAttributes(buildBean.getDataItems());
        classModel.setAttributes(attributes);
        //类导入
        Set<Class<?>> classImports = getClassImports(classModel);
        classModel.setClassImports(classImports);
        //属性方法
        Map<String, MethodMeta> attributeMethods = getAttributeMethods(attributes,classImports);
        classModel.setMethods(attributeMethods);
        return classModel;
    }


    /**获取声明
     * 2022/9/15 0015-15:32
     * @author pengfulin
     */
    protected ClassMetaStatement getClassMetaStatement(BuildBean buildBean){
        ClassMetaStatement classMetaStatement = new ClassMetaStatement();
        classMetaStatement.setClassName(ClassUtil.getClassStructureName(buildBean.getName(),"_", ClassStructure.CLASS_DECLARE));
        String description = buildBean.getDescription();
        if(!StringUtil.isEmpty(description)){
            classMetaStatement.getClassComment().setDescription(description);
        }else{
            classMetaStatement.setClassComment(null);
        }
        return classMetaStatement;
    }

    /**获取类导入
     * 2022/9/15 0015-15:37
     * @author pengfulin
     */
    protected Set<Class<?>> getClassImports(ClassModel classModel){
        Set<Class<?>> classImports = new LinkedHashSet<>();
        classImports.addAll(resolveClassStatementImports(
                classModel.getClassMetaStatement()
        ));
        classImports.addAll(resolveAttributeImports(
                classModel.getAttributes()
        ));
        return classImports;
    }

    /**获取属性
     * 2022/9/15 0015-15:25
     * @author pengfulin
     */
    protected Map<String,FieldMeta> getAttributes(List<BuildBeanItem> dataItems){
        Map<String, FieldMeta> attributes = new LinkedHashMap<>();
        dataItems.forEach(value->{
            FieldMeta fieldMeta = new FieldMeta();
            fieldMeta.setFieldName(ClassUtil.getClassStructureName(value.getFieldName(),
                    "_",ClassStructure.CLASS_ATTRIBUTE));
            String fieldComment = value.getItemConfig().getFieldComment();
            if(!StringUtil.isEmpty(fieldComment)){
                fieldMeta.getFieldComment().setDescription(fieldComment);
            }else{
                fieldMeta.setFieldComment(null);
            }
            fieldMeta.setFieldType(FieldType.supportType(value.getFieldType()));
            attributes.put(fieldMeta.getFieldName(),fieldMeta);
        });
        return attributes;
    }

    /**获取属性方法
     * 2022/9/15 0015-15:31
     * @author pengfulin
    */
    protected  Map<String,MethodMeta> getAttributeMethods(Map<String, FieldMeta> fields, Set<Class<?>> importClasses){return null;}
}