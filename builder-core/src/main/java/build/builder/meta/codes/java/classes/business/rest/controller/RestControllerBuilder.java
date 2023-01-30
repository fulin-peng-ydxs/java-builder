package build.builder.meta.codes.java.classes.business.rest.controller;

import build.builder.data.classes.meta.AnnotationMeta;
import build.builder.data.classes.meta.BusinessClass;
import build.builder.data.classes.meta.ClassMetaStatement;
import build.builder.data.classes.meta.ParamMeta;
import build.builder.data.classes.model.ClassModel;
import build.builder.meta.codes.java.classes.business.rest.RestBusinessBuilder;
import build.source.data.business.rest.meta.RestBusiness;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.lang.annotation.Annotation;
import java.util.*;

/**
 * Rest风格的Controller构建器
 *
 * @author peng_fu_lin
 * 2022-11-16 11:12
 */
public class RestControllerBuilder extends RestBusinessBuilder {

    public RestControllerBuilder(){
        super.packageStatement="java.controller";
    }

    @Override
    protected ClassModel getBuildModel(Object buildDataModel) {
        ClassModel buildModel = super.getBuildModel(buildDataModel);
        return configInterfaceDetail(buildModel,(RestBusiness)buildDataModel);
    }


    /**配置接口详情
     * 2022/12/22 0022-11:44
     * @author pengfulin
     */
    protected ClassModel configInterfaceDetail(ClassModel classModel,RestBusiness restBusiness){
        //类声明
        ClassMetaStatement classMetaStatement = classModel.getClassMetaStatement();
        Map<Class<? extends Annotation>, AnnotationMeta> classAnnotations = classMetaStatement.getClassAnnotations()==null?
                new LinkedHashMap<>():classMetaStatement.getClassAnnotations();
        classAnnotations.remove(Controller.class);
        classAnnotations.put(RestController.class,null);
        classMetaStatement.setClassAnnotations(classAnnotations);

        //类方法
        classModel.getMethods().forEach((key,value)->{
            List<ParamMeta> methodParams = value.getMethodParams();
            Map<Class<? extends Annotation>, AnnotationMeta> methodAnnotations =value.getMethodAnnotations()==null?
                    new LinkedHashMap<>():value.getMethodAnnotations();
            String methodName = value.getMethodName();
            if(methodParams==null)
                methodAnnotations.put(GetMapping.class,new AnnotationMeta(null,Collections.singletonMap("value", methodName)));
            else{
                for (ParamMeta methodParam : methodParams) {
                    Map<Class<? extends Annotation>, AnnotationMeta> paramAnnotations = methodParam.getParamAnnotations()==null ?
                            new LinkedHashMap<>(): methodParam.getParamAnnotations();
                    if (methodParam.getParamType()== BusinessClass.class ||Collection.class.isAssignableFrom(methodParam.getParamType())) {
                        if(methodName.contains("find"))
                            methodAnnotations.put(PostMapping.class,doGetPathMapping(methodName,methodParam.getParamName()));
                        else if(methodName.contains("update"))
                            methodAnnotations.put(PutMapping.class,doGetPathMapping(methodName,methodParam.getParamName()));
                        else if(methodName.contains("delete"))
                            methodAnnotations.put(DeleteMapping.class,doGetPathMapping(methodName,methodParam.getParamName()));
                        paramAnnotations.put(RequestBody.class,null);
                    }else if(methodParam.getParamType()==restBusiness.getBuildBeanInfo().getPrimaryKeyType()){
                        methodAnnotations.put(GetMapping.class,doGetPathMapping(methodName,methodParam.getParamName()));
                        paramAnnotations.put(PathVariable.class,null);
                    }
                    methodParam.setParamAnnotations(paramAnnotations);
                }
            }
            value.setMethodParams(methodParams);
            value.setMethodAnnotations(methodAnnotations);
        });
        return classModel;
    }

    /**生成路径请求映射
     * 2022/12/27 0027-10:31
     * @author pengfulin
    */
    protected AnnotationMeta doGetPathMapping(String pathName,String mappingName){
        return new AnnotationMeta(GetMapping.class,Collections.singletonMap("value", String.format("/%s/{%s}", pathName,mappingName)));
    }
}