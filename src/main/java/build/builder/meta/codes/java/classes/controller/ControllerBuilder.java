package build.builder.meta.codes.java.classes.controller;


import build.builder.data.classes.meta.ClassMetaStatement;
import build.builder.data.classes.meta.FieldMeta;
import build.builder.data.classes.meta.MethodMeta;
import build.builder.data.classes.model.ClassModel;
import build.builder.meta.codes.java.classes.ClassBuilder;
import build.source.data.meta.business.AbstractBusinessModel;
import build.source.data.meta.business.controller.ControllerBean;
import java.util.List;
import java.util.Map;

/**
 * 控制器构建
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
        //查询接口
        //删除接口
        //新增接口
        //更新接口
        return null;
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
    protected Map<String, FieldMeta> getAttribute(ControllerBean controllerBean){
        return null;
    }


    /**获取查询接口
     * 2022/11/4 0004-14:57
     * @author pengfulin
    */
    protected List<MethodMeta> getQueryInterface(){
        return null;
    }


    /**获取删除接口
     * 2022/11/4 0004-15:00
     * @author pengfulin
    */
    protected List<MethodMeta> getDeleteInterface(){
        return null;
    }


    /**获取更新接口
     * 2022/11/4 0004-15:01
     * @author pengfulin
    */
    protected List<MethodMeta> getUpdateInterface(){
        return null;
    }
    
    
    /**获取新增接口
     * 2022/11/4 0004-15:04
     * @author pengfulin
    */
    protected List<MethodMeta> getAddInterface(){
        return null;
    }
}