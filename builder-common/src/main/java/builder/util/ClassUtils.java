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
public class ClassUtils {

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
        return StringUtils.builderToString(builder);
    }

    /**
     * 类转属性名
     * 2023/9/23 22:27
     * @author pengshuaifeng
     */
    public static String nameToAttribute(String value){
        String last = value.substring(1);
        String start = value.substring(0, 1).toLowerCase();
        return start+last;
    }

    /**
     * 属性转类名
     * 2023/9/23 22:40
     * @author pengshuaifeng
     */
    public static String attributeToName(String value){
        String last = value.substring(1);
        String start = value.substring(0, 1).toUpperCase();
        return start+last;
    }



    /**
     * 生成引用路径（包路径）
     * 2023/9/3 22:29
     * @author pengshuaifeng
     * @param path 原路径
     */
    public static String generateReferencePath(String path){
        if(path.contains("java")){ //截取java后面的路径
            path=StringUtils.substring(path, "java",null,false,true);
        }
        path= StringUtils.clearChar(path,File.separatorChar,true);
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

    /**
     * 类型是否一致
     * @param clazz1 同类或子类
     * @param clazz2 同类或父类
     * 2023/12/24 23:20
     * @author pengshuaifeng
     */
    public static boolean typeEquals(Class<?> clazz1,Class<?> clazz2){
        return clazz1 == clazz2 || clazz2.isAssignableFrom(clazz1);
    }
}