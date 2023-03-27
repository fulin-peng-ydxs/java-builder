package build.builder.meta.codes.java.classes.business.rest;

import build.builder.data.busness.RestMethod;
import build.builder.data.classes.enums.ClassStructure;
import build.builder.data.classes.enums.FieldType;
import build.builder.data.classes.meta.*;
import build.builder.data.classes.model.ClassModel;
import build.builder.meta.codes.java.classes.ClassBuilder;
import build.builder.util.ClassBuildUtil;
import build.builder.util.CollectionUtil;
import build.source.data.business.meta.AbstractBusinessModel;
import build.source.data.business.rest.RestServiceInfo;
import build.source.data.business.rest.meta.RestBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import java.lang.annotation.Annotation;
import java.util.*;
/**
 * Rest业务构建器
 *
 * @author peng_fu_lin
 * 2022-12-20 14:30
 */
public abstract class RestBusinessBuilder extends ClassBuilder {

    protected final String useFormatter="%sreturn %s.%s(%s);\n";


    @Override
    public Class<?> supportedBuildSource() {return RestBusiness.class;}

    @Override
    protected ClassModel getBuildModel(Object buildDataModel) {
        if(buildDataModel instanceof RestBusiness)
            return doGetRestServiceModel((RestBusiness) buildDataModel);
        return null;
    }


    /**生成服务模型
     * 2022/11/4 0004-14:43
     * @author pengfulin
     */
    protected ClassModel doGetRestServiceModel(RestBusiness restBusiness){
        ClassModel classModel = defaultClassModel();
        //类声明
        classModel.setClassMetaStatement(getClassMetaStatement(restBusiness));
        //类属性
        classModel.setAttributes(getAttributes(restBusiness));
        //类导入
        Set<Class<?>> classImports = getClassImports(classModel);
        classModel.setClassImports(classImports);
        Map<String, MethodMeta> methods=new LinkedHashMap<>();
        //查询接口
        methods.putAll(getQueryInterface(restBusiness,classImports));
        //删除接口
        methods.putAll(getDeleteInterface(restBusiness,classImports));
        //新增接口
        methods.putAll(getAddInterface(restBusiness,classImports));
        //更新接口
        methods.putAll(getUpdateInterface(restBusiness,classImports));
        classModel.setMethods(methods);
        return classModel;
    }

    /**获取声明
     * 2022/11/4 0004-17:11
     * @author pengfulin
     */
    protected ClassMetaStatement getClassMetaStatement(RestBusiness restBusiness){
        ClassMetaStatement classMetaStatement = new ClassMetaStatement();
        RestServiceInfo restServiceInfo = restBusiness.getRestServiceInfo();
        classMetaStatement.setClassName(restServiceInfo.getServiceName()); //类名
        classMetaStatement.getClassComment().setDescription(restServiceInfo.getDescription()); //类注释
        return classMetaStatement;
    }

    /**获取属性
     * 2022/11/4 0004-17:36
     * @author pengfulin
     */
    protected  Map<String, FieldMeta> getAttributes(RestBusiness restBusiness){
        Map<String, FieldMeta> attributes = new HashMap<>();
        FieldMeta fieldMeta = doGetOperationAttribute(restBusiness.getRestServiceInfo());
        attributes.put(fieldMeta.getFieldName(),fieldMeta);
        return attributes;
    }


    /**获取查询接口
     * 2022/11/4 0004-14:57
     * @author pengfulin
     */
    protected  Map<String, MethodMeta> getQueryInterface(RestBusiness restBusiness,Set<Class<?>> importClasses){
        Map<String, MethodMeta> methods=CollectionUtil.doGetAnyMap(LinkedHashMap.class,String.class,MethodMeta.class,null);
        MethodMeta listQueryInterface = getListQueryInterface(restBusiness, importClasses);
        methods.put(listQueryInterface.getMethodName(),listQueryInterface);
        MethodMeta detailQueryInterface = getDetailQueryInterface(restBusiness, importClasses);
        methods.put(detailQueryInterface.getMethodName(),detailQueryInterface);
        return methods;
    }

    /**获取查询列表接口
     * 2022/12/20 0020-16:02
     * @author pengfulin
     */
    protected MethodMeta getListQueryInterface(RestBusiness restBusiness, Set<Class<?>> importClasses){
        return useServiceEntityStyle(RestMethod.FIND.name(),restBusiness,List.class,importClasses);
    }

    /**获取查询详情接口
     * 2022/12/20 0020-16:16
     * @author pengfulin
     */
    protected MethodMeta getDetailQueryInterface(RestBusiness restBusiness, Set<Class<?>> importClasses){
        return usePrimaryStyle("detail",restBusiness,BusinessClass.class,true,importClasses);
    }


    /**获取删除接口
     * 2022/11/4 0004-15:00
     * @author pengfulin
     */
    protected  Map<String, MethodMeta> getDeleteInterface(RestBusiness restBusiness,Set<Class<?>> importClasses){
        Map<String, MethodMeta> methods=new LinkedHashMap<>();
        MethodMeta deleteOnlyInterface = getDeleteOnlyInterface(restBusiness, importClasses);
        methods.put(deleteOnlyInterface.getMethodName(),deleteOnlyInterface);
        MethodMeta deleteBatchInterface = getDeleteBatchInterface(restBusiness, importClasses);
        methods.put(deleteBatchInterface.getMethodName(),deleteBatchInterface);
        return methods;
    }

    /**获取单个删除接口
     * 2023/1/13 0013-11:38
     * @author pengfulin
    */
    public MethodMeta getDeleteOnlyInterface(RestBusiness restBusiness,Set<Class<?>> importClasses){
        return usePrimaryStyle(RestMethod.DELETE.name(), restBusiness,Integer.class,true,importClasses);
    }

    /**获取批量删除接口
     * 2023/1/13 0013-11:41
     * @author pengfulin
    */
    public MethodMeta getDeleteBatchInterface(RestBusiness restBusiness,Set<Class<?>> importClasses){
        return usePrimaryStyle(RestMethod.DELETE_BATCH.name(), restBusiness,Integer.class,false,importClasses);
    }


    /**使用主键调用风格
     * 2023/1/13 0013-11:46
     * @author pengfulin
    */
    protected MethodMeta usePrimaryStyle(String methodName,RestBusiness restBusiness,Class<?> returnType,boolean isOnly,Set<Class<?>> importClasses){
        MethodMeta methodMeta = doGetDefaultMethod(methodName);
        AbstractBusinessModel.BeanInfo beanInfo = restBusiness.getBeanInfo();
        AbstractBusinessModel.BuildBeanInfo buildBeanInfo = restBusiness.getBuildBeanInfo();
        //设置参数
        List<ParamMeta> methodParams = CollectionUtil.doGetAnyList(LinkedList.class,ParamMeta.class,methodMeta.getMethodParams());
        if(isOnly)
            methodParams.add(new ParamMeta(buildBeanInfo.getPrimaryKey()
                ,buildBeanInfo.getPrimaryKeyType(),null,null, null));
        else
            methodParams.add(new ParamMeta(buildBeanInfo.getPrimaryKey()+"s"
                    ,List.class,Collections.singletonList(new GenericMeta(buildBeanInfo.getPrimaryKeyType(),null)),null, null));
        methodMeta.setMethodParams(methodParams);
        //设置方法内容
        methodMeta.setMethodContent(doGetServiceUse(restBusiness.getRestServiceInfo().getDependentService().getServiceName(),methodMeta.getMethodName(),buildBeanInfo.getPrimaryKey()));
        //设置方法返回类型
        if(returnType==BusinessClass.class)
            setReturnType(methodMeta,doGetBusinessClass(beanInfo.getSimpleName(), beanInfo.getPackages()),BusinessClass.class);
        else setReturnType(methodMeta,null,returnType);
        return methodMeta;
    }


    /**获取更新接口
     * 2022/11/4 0004-15:01
     * @author pengfulin
     */
    protected   Map<String, MethodMeta> getUpdateInterface(RestBusiness restBusiness, Set<Class<?>> importClasses){
        MethodMeta methodMeta = useServiceEntityStyle(RestMethod.UPDATE.name(), restBusiness,BusinessClass.class,importClasses);
        return Collections.singletonMap(methodMeta.getMethodName(),methodMeta);
    }


    /**获取新增接口
     * 2022/11/4 0004-15:04
     * @author pengfulin
     */
    protected   Map<String, MethodMeta> getAddInterface(RestBusiness restBusiness,Set<Class<?>> importClasses){
        MethodMeta methodMeta = useServiceEntityStyle(RestMethod.ADD.name(), restBusiness,BusinessClass.class,importClasses);
        return Collections.singletonMap(methodMeta.getMethodName(),methodMeta);
    }

    /**使用服务调用风格
     * 2022/12/27 0027-11:08
     * @author pengfulin
     */
    protected MethodMeta useServiceEntityStyle(String methodName,RestBusiness restBusiness,Class<?> returnType,Set<Class<?>> importClasses){
        MethodMeta methodMeta = doGetDefaultMethod(methodName);
        AbstractBusinessModel.BeanInfo beanInfo = restBusiness.getBeanInfo();
        BusinessClass businessClass =doGetBusinessClass(beanInfo.getSimpleName(), beanInfo.getPackages());
        String paramName = ClassBuildUtil.getClassStructureName(restBusiness.getBuildBeanInfo().getName(), "_", ClassStructure.CLASS_ATTRIBUTE);
        //设置参数
        List<ParamMeta> methodParams = CollectionUtil.doGetAnyList(LinkedList.class,ParamMeta.class,methodMeta.getMethodParams());
        methodParams.add(doGetOperationParam(paramName,businessClass));
        methodMeta.setMethodParams(methodParams);
        //设置方法内容
        methodMeta.setMethodContent(doGetServiceUse(restBusiness.getRestServiceInfo().getDependentService().getServiceName(),methodMeta.getMethodName(),paramName));
        //设置方法返回类型
        setReturnType(methodMeta, businessClass,returnType);
        return methodMeta;
    }


    /**生成默认方法
     * 2022/12/27 0027-15:35
     * @author pengfulin
     */
    protected MethodMeta doGetDefaultMethod(String methodName){
        MethodMeta methodMeta = new MethodMeta();
        methodMeta.setMethodName(methodName);
        return methodMeta;
    }



    /**生成操作对象参数
     * 2022/12/27 0027-16:40
     * @author pengfulin
     */
    protected ParamMeta doGetOperationParam(String operationName,BusinessClass businessClass){
        return new ParamMeta(operationName,BusinessClass.class,null,null, businessClass);
    }

    /**生成服务的调用
     * 2022/12/27 0027-10:40
     * @author pengfulin
     */
    protected String doGetServiceUse(String serviceName,String methodName,String methodParam){
        return String.format(useFormatter,structureInternalIndentation(),
                ClassBuildUtil.getClassStructureName(serviceName,"",ClassStructure.CLASS_ATTRIBUTE), methodName,methodParam);
    }

    /**设置返回类型
     * 2023/1/3 0003-16:04
     * @author pengfulin
     */
    protected void setReturnType(MethodMeta methodMeta, BusinessClass businessClass,Class<?> returnType){
        //设置方法返回类型
        if(returnType==List.class)
            methodMeta.setMethodReturn(new MethodMeta.ReturnMeta(List.class,Collections.singletonList(new GenericMeta(BusinessClass.class,null)), businessClass));
        else if(returnType==BusinessClass.class)
            methodMeta.setMethodReturn(new MethodMeta.ReturnMeta(BusinessClass.class,null,businessClass));
        else
            methodMeta.setMethodReturn(new MethodMeta.ReturnMeta(returnType,null,null));
    }


    /**生成操作对象属性
     * 2022/12/27 0027-16:12
     * @author pengfulin
     */
    protected FieldMeta doGetOperationAttribute(RestServiceInfo restServiceInfo){
        FieldMeta fieldMeta = new FieldMeta();
        fieldMeta.setFieldName(ClassBuildUtil.getClassStructureName(restServiceInfo.getServiceName(), "", ClassStructure.CLASS_ATTRIBUTE));
        fieldMeta.setFieldType(FieldType.BUSINESS);
        fieldMeta.getFieldComment().setDescription(restServiceInfo.getDescription());
        fieldMeta.setBusinessClass(new BusinessClass(restServiceInfo.getServiceName(), restServiceInfo.getDescription()));
        setAttributeValue(fieldMeta);
        return fieldMeta;
    }

    /**设置属性注入
     * 2022/12/20 0020-14:06
     * @author pengfulin
     */
    protected void setAttributeValue(FieldMeta fieldMeta){
        Map<Class<? extends Annotation>, AnnotationMeta> fieldAnnotations = fieldMeta.getFieldAnnotations();
        if(fieldAnnotations!=null)
            fieldAnnotations.put(Autowired.class,null);
        else
            fieldMeta.setFieldAnnotations(Collections.singletonMap(Autowired.class,null));
    }

    /**生成默认业务对象
     * 2023/1/13 0013-12:08
     * @author pengfulin
    */
    protected BusinessClass doGetBusinessClass(String businessName,String businessPackage){
        return new BusinessClass(businessName,businessPackage);
    }
}