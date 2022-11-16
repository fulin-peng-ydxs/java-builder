package build.builder.meta.codes.java.classes.controller;

import build.builder.data.classes.enums.ClassStructure;
import build.builder.data.classes.enums.FieldType;
import build.builder.data.classes.meta.BusinessClass;
import build.builder.data.classes.meta.ClassMetaStatement;
import build.builder.data.classes.meta.FieldMeta;
import build.builder.data.classes.meta.MethodMeta;
import build.builder.data.classes.model.ClassModel;
import build.builder.meta.codes.java.classes.ClassBuilder;
import build.builder.util.ClassBuildUtil;
import build.source.data.meta.business.AbstractBusinessModel;
import build.source.data.meta.business.controller.ControllerBean;

import java.util.*;

/**
 * Controller构建器
 *
 * @author peng_fu_lin
 * 2022-10-20 16:31
 */
public abstract class ControllerBuilder extends ClassBuilder {

    public ControllerBuilder(){
        super.packageStatement="java.controller";
    }

    @Override
    public Class<?> supportedBuildSource() {return ControllerBean.class;}

    @Override
    protected ClassModel getBuildModel(Object buildDataModel) {
        if(buildDataModel instanceof ControllerBean)
            return doGetControllerModel((ControllerBean) buildDataModel);
        return null;
    }

    /**生成controller模型
     * 2022/11/4 0004-14:43
     * @author pengfulin
    */
    public ClassModel doGetControllerModel(ControllerBean controllerBean){
        ClassModel classModel = defaultClassModel();
        //类声明
        classModel.setClassMetaStatement(getClassMetaStatement(controllerBean));
        //类属性
        classModel.setAttributes(getAttribute(controllerBean));
        Map<String, MethodMeta> methods=new LinkedHashMap<>();
        HashSet<Class<?>> classImports = new HashSet<>();
        //查询接口
        methods.putAll(getQueryInterface(controllerBean,classImports));
        //删除接口
        methods.putAll(getDeleteInterface(controllerBean,classImports));
        //新增接口
        methods.putAll(getAddInterface(controllerBean,classImports));
        //更新接口
        methods.putAll(getUpdateInterface(controllerBean,classImports));
        classModel.setMethods(methods);
        return classModel;
    }

    /**获取声明
     * 2022/11/4 0004-17:11
     * @author pengfulin
    */
    protected ClassMetaStatement getClassMetaStatement(ControllerBean controllerBean){
        ClassMetaStatement classMetaStatement = new ClassMetaStatement();
        AbstractBusinessModel.BeanInfo beanInfo = controllerBean.getBeanInfo();
        classMetaStatement.setClassName(beanInfo.getSimpleName()+"Controller"); //类名
        classMetaStatement.getClassComment().setDescription(beanInfo.getDescription()+"控制器"); //类注释
        return classMetaStatement;
    }

    /**获取属性
     * 2022/11/4 0004-17:36
     * @author pengfulin
    */
    protected  Map<String, FieldMeta> getAttribute(ControllerBean controllerBean){
        Map<String, FieldMeta> attributes = new HashMap<>();
        FieldMeta fieldMeta = new FieldMeta();
        ControllerBean.ServiceInfo serviceInfo = controllerBean.getServiceInfo();
        String serviceFieldName =
                ClassBuildUtil.getClassStructureName(serviceInfo.getName(), "", ClassStructure.CLASS_ATTRIBUTE);
        fieldMeta.setFieldName(serviceFieldName);
        fieldMeta.setFieldType(FieldType.BUSINESS);
        fieldMeta.getFieldComment().setDescription(serviceInfo.getDescription());
        fieldMeta.setBusinessClass(new BusinessClass(serviceInfo.getName(),
                serviceInfo.getPackages()));
        attributes.put(serviceFieldName,fieldMeta);
        return attributes;
    }


    /**获取查询接口
     * 2022/11/4 0004-14:57
     * @author pengfulin
    */
    protected abstract Map<String, MethodMeta> getQueryInterface(ControllerBean controllerBean,Set<Class<?>> importClasses);


    /**获取删除接口
     * 2022/11/4 0004-15:00
     * @author pengfulin
    */
    protected abstract Map<String, MethodMeta> getDeleteInterface(ControllerBean controllerBean,Set<Class<?>> importClasses);


    /**获取更新接口
     * 2022/11/4 0004-15:01
     * @author pengfulin
    */
    protected abstract  Map<String, MethodMeta> getUpdateInterface(ControllerBean controllerBean,Set<Class<?>> importClasses);
    
    
    /**获取新增接口
     * 2022/11/4 0004-15:04
     * @author pengfulin
    */
    protected abstract  Map<String, MethodMeta> getAddInterface(ControllerBean controllerBean,Set<Class<?>> importClasses);
}