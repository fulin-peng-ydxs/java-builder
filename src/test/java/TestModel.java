import build.builder.meta.codes.java.classes.bean.LombokBeanBuilder;
import build.builder.meta.codes.java.classes.bean.SimpleBeanBuilder;
import build.bus.exception.BuildBusException;
import build.bus.model.SimpleBuildBus;
import build.source.meta.jdbc.JdbcBuildFileSource;
import com.mysql.jdbc.Driver;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

/**
 * 构建总线测试
 *
 * @author peng_fu_lin
 * 2022-09-05 11:19
 */
public class TestModel {

    JdbcBuildFileSource jdbcBuildFileSource=null;


    @Before
    public void before(){
        //数据源
        jdbcBuildFileSource = new JdbcBuildFileSource();
        jdbcBuildFileSource.setUrl("jdbc:mysql://130.120.2.219:3306/fullres_test_prod");
        jdbcBuildFileSource.setDataBaseName("fullres_test_prod");
        jdbcBuildFileSource.setTableNames(Collections.singletonList("sharing_special"));
        jdbcBuildFileSource.setUserName("fullres");
        jdbcBuildFileSource.setPassword("123456");
        jdbcBuildFileSource.setDriverClass(Driver.class);
    }

    /**简单构建总线的测试
     * 2022/9/19 0019-15:14
     * @author pengfulin
    */
    @Test
    public void testBus() throws BuildBusException {
        //构建总线
        SimpleBuildBus simpleBuildBus = new SimpleBuildBus();
        //执行构建
        simpleBuildBus.build(SimpleBeanBuilder.class, jdbcBuildFileSource,null);
    }

    @Test
    public void testLombokBeanBuilder() throws BuildBusException {
        //构建总线
        SimpleBuildBus simpleBuildBus = new SimpleBuildBus();
        //执行构建
        simpleBuildBus.build(LombokBeanBuilder.class, jdbcBuildFileSource,null);
    }
}