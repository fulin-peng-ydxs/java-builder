package build.builder.meta.codes.java.classes.bean;

import build.builder.data.classes.enums.ClassStructure;
import build.builder.data.classes.meta.FieldMeta;
import build.builder.data.classes.meta.MethodMeta;
import build.builder.data.classes.meta.ParamMeta;
import build.builder.util.ClassBuildUtil;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * 简单的Bean构建器
 * <p>：构建传统的bean</p>
 * @author peng_fu_lin
 * 2022-09-15 10:31
 */
public class SimpleBeanBuilder extends BeanBuilder{

    @Override
    protected Map<String, MethodMeta> getAttributeMethods(Map<String, FieldMeta> fields, Set<Class<?>> importClasses) {
        LinkedHashMap<String, MethodMeta> fieldMethods = new LinkedHashMap<>();
        fields.forEach((key,value)->{
            //Get方法
            MethodMeta getMethodMeta = new MethodMeta();
            getMethodMeta.setMethodName(ClassBuildUtil.getClassStructureName("get"+"_"+
                    value.getFieldName(),"_", ClassStructure.CLASS_METHOD));
            getMethodMeta.setMethodReturn(value.getFieldType().java);
            String getMethodTemplate="%sreturn%s%s;\n";
            String methodContent = String.format(getMethodTemplate, structureInternalIndentation(), codeSpaceStyle()
                    ,key);
            getMethodMeta.setMethodContent(methodContent);
            //Set方法
            MethodMeta setMethodMeta = new MethodMeta();
            setMethodMeta.setMethodName(ClassBuildUtil.getClassStructureName("set"+"_"+
                    value.getFieldName(),"_", ClassStructure.CLASS_METHOD));
            setMethodMeta.setMethodReturn(void.class);
            String setMethodTemplate="%sthis.%s%s=%s%s;\n";
            String setMethodContent = String.format(setMethodTemplate, structureInternalIndentation(),
                    key,codeSpaceStyle(),codeSpaceStyle(),key);
            setMethodMeta.setMethodContent(setMethodContent);
            ParamMeta paramMeta = new ParamMeta();
            paramMeta.setParamName(key);
            paramMeta.setParamType(value.getFieldType().java);
            setMethodMeta.setMethodParams(Collections.singletonList(paramMeta));
            fieldMethods.put(getMethodMeta.getMethodName(),getMethodMeta);
            fieldMethods.put(setMethodMeta.getMethodName(),setMethodMeta);
        });
        return fieldMethods;
    }
}