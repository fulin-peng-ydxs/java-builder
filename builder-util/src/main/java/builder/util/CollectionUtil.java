package builder.util;

import java.util.*;

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
    @SafeVarargs
    public static <T> List<T> asList(T... a){
        T[] array = Objects.requireNonNull(a);
        List<T> list = new ArrayList<>();
        for (T t : array) {
            if(t!=null)
                list.add(t);
        }
        return list;
    }


    /**生成任何类型List的集合：仅无参构造
     * 2023/1/13 0013-10:51
     * @author pengfulin
     * @param target 要生成的集合类型
     * @param source 集合源，如果不为null，则直接返回该对象
    */
    public static <T,B> List<B> doGetAnyList(Class<T> target,Class<B> targetParam,List<B> source){
        try {
            if (target.isAssignableFrom(List.class))
                throw new RuntimeException("类型不匹配");
            if(source!=null)
                return source;
            else return (List<B>)target.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("生成任何类型List集合异常",e);
        }
    }


    /**生成任何类型Map：仅无参构造
     * 2023/1/13 0013-14:00
     * @param target 要生成的Map类型
     * @param targetKey 要生成的Map键类型
     * @param targetValue 要生成的Map值类型
     * @param source 集合源，如果不为null，则直接返回该对象
     * @author pengfulin
    */
    public static <A,B,C> Map<A,B> doGetAnyMap(Class<C> target,Class<A> targetKey,Class<B> targetValue,Map<A,B> source){
        try {
            if (target.isAssignableFrom(Map.class))
                throw new RuntimeException("类型不匹配");
            if(source!=null)
                return source;
            else return (Map<A, B>)target.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("生成任何类型Map异常",e);
        }
    }
}