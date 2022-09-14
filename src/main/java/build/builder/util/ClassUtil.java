package build.builder.util;

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
}