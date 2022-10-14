package build.builder.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

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

    /**是否为空
     * 2022/9/29 0029-14:00
     * @author pengfulin
    */
    public static boolean isEmpty(Collection<?> collection){
        return collection==null||collection.isEmpty();
    }

    public static boolean isNotEmpty(Collection<?> collection){
        return !isEmpty(collection);
    }
    
    
    /**生成集合
     * 2022/10/14 0014-10:27
     * @param a 待加入集合的可变数组，会忽略为null的元素
     * @author pengfulin
    */
    public static <T> List<T> asList(T... a){
        T[] array = Objects.requireNonNull(a);
        List<T> list = new ArrayList<>();
        for (T t : array) {
            if(t!=null)
                list.add(t);
        }
        return list;
    }
}