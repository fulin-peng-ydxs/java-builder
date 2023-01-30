package build.builder.meta.codes.java.classes.bean;

import build.builder.data.classes.enums.ClassStructure;
import build.builder.data.classes.enums.FieldType;
import build.builder.data.classes.meta.*;
import build.builder.data.classes.model.ClassModel;
import build.builder.meta.codes.java.classes.ClassBuilder;
import build.builder.util.ClassBuildUtil;
import build.builder.util.StringBuildUtil;
import build.source.data.bean.BuildBean;
import build.source.data.bean.BuildBeanItem;
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
        ClassModel classModel = defaultClassModel();
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
        //类名
        classMetaStatement.setClassName(ClassBuildUtil.getClassStructureName(buildBean.getName(),"_", ClassStructure.CLASS_DECLARE));
        //类注释
        String description = buildBean.getDescription();
        if(StringBuildUtil.isEmpty(description))
            description=buildBean.getName()+"表实体";
        classMetaStatement.getClassComment().setDescription(description);
        return classMetaStatement;
    }

    /**获取属性
     * 2022/9/15 0015-15:25
     * @author pengfulin
     */
    protected Map<String,FieldMeta> getAttributes(List<BuildBeanItem> dataItems){
        Map<String, FieldMeta> attributes = new LinkedHashMap<>();
        dataItems.forEach(value->{
            FieldMeta fieldMeta = new FieldMeta();
            fieldMeta.setFieldName(ClassBuildUtil.getClassStructureName(value.getFieldName(),
                    "_",ClassStructure.CLASS_ATTRIBUTE));
            String fieldComment = value.getItemConfig().getFieldComment();
            if(!StringBuildUtil.isEmpty(fieldComment)){
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