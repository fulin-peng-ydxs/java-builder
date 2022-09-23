package build.builder.meta.codes.persist;

/**
 * 持续集成的代码构建器
 *
 * @author peng_fu_lin
 * 2022-09-23 11:11
 */
public interface  PersistCodeBuilder<T> {

    /**可解析的持续集成源
     * 2022/9/23 0023-14:40
     * @author pengfulin
     */
    default Class<?> analysable() {throw new RuntimeException("an unsupported method");}

    /**解析持续集成数据源
     * 2022/9/23 0023-15:28
     * @author pengfulin
    */
     default T resolvePersistSource(Object persistSource) {throw new RuntimeException("an unsupported method");}
}