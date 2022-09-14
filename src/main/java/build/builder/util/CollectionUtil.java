package build.builder.util;

import java.util.List;

/**
 * 集合工具类
 *
 * @author peng_fu_lin
 * 2022-09-07 15:55
 */
public class CollectionUtil {


    /**强制转换泛型集合
     * 2022/9/7 0007-16:00
     * @author pengfulin
    */
    public static  <T>  List<T> getTargetList(Object object, Class<T> target){
        return (List<T>)object;
    }
}