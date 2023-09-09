package builder.util;

import builder.model.build.config.enums.ClassStructure;
import java.io.File;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

/**
 * Class构建工具类
 *
 * @author peng_fu_lin
 * 2022-09-07 15:17
 */
public class ClassUtil {

    /**获取泛型
     * 2022/9/7 0007-15:17
     * @author pengfulin
     * @param source 类对象
    */
    public static List<Class<?>> getParamTypes(Class<?> source){
        List<Class<?>> paramTypes=null;
        Type genericSuperclass = source.getGenericSuperclass();
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
     * @param splitValue 源数据
     * @param splitSymbol 源数据分割符
     * @param classStructure 要获取的类结构
    */
    public static String generateStructureName(String splitValue, String splitSymbol,ClassStructure classStructure){
        StringBuilder builder = new StringBuilder();
        String[] split = splitValue.split(splitSymbol);
        boolean isContinue=true;
        for (int i = 0; i < split.length; i++) {
            split[i]=split[i].trim();
            if(classStructure!=ClassStructure.NAME &&isContinue){
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
        return StringUtil.builderToString(builder);
    }

    /**
     * 生成引用路径
     * 2023/9/3 22:29
     * @author pengshuaifeng
     * @param path 原路径
     */
    public static String generateReferencePath(String path){
        return path.replace(File.separator, ".");
    }

    /**
     * 可忽略引用导入
     * 2023/9/8 23:35
     * @author pengshuaifeng
     */
    public static boolean ignoreReference(String reference){
        return reference.startsWith("java.lang");
    }

    public static String ignoreReferenceToEmpty(String reference){
        if (ignoreReference(reference)) {
            return "";
        }
        return reference;
    }
}