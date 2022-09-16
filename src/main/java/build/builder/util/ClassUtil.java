package build.builder.util;

import build.builder.data.classes.enums.ClassStructure;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

/**
 * Class反射工具类
 *
 * @author peng_fu_lin
 * 2022-09-07 15:17
 */
public class ClassUtil{

    /**获取泛型
     * 2022/9/7 0007-15:17
     * @author pengfulin
    */
    public static List<Class<?>> getParamTypes(Class<?> sourceClass){
        List<Class<?>> paramTypes=null;
        Type genericSuperclass = sourceClass.getGenericSuperclass();
        if(genericSuperclass instanceof ParameterizedType){
            paramTypes=new LinkedList<>();
            for (Type typeArgument : ((ParameterizedType) genericSuperclass).getActualTypeArguments()) {
                paramTypes.add((Class<?>)typeArgument);
            }
        }
        return paramTypes;
    }


    /**生成命名
     * 2022/9/15 0015-15:49
     * @author pengfulin
    */
    public static String getClassStructureName(String structureValue,String splitValue,
                                               ClassStructure classStructure){
        StringBuilder builder = new StringBuilder();
        String[] split = structureValue.split(splitValue);
        boolean isContinue=true;
        for (int i = 0; i < split.length; i++) {
            split[i]=split[i].trim();
            if(classStructure!=ClassStructure.CLASS_DECLARE &&isContinue){
                String lowerCase = split[i].toLowerCase();
                split[i]= lowerCase;
                isContinue=false;
                builder.append(split[i]);
                continue;
            }
            String lowerCase = split[i].toLowerCase();
            split[i]= lowerCase.substring(0,1).toUpperCase()+lowerCase.substring(1);
            builder.append(split[i]);
        }
        return builder.toString();
    }
}