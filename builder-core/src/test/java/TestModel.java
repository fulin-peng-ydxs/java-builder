import build.builder.meta.codes.java.classes.bean.LombokBeanBuilder;
import build.builder.meta.codes.java.classes.bean.SimpleBeanBuilder;
import build.bus.exception.BuildBusException;
import build.bus.model.simple.SimpleBuildBus;
import build.source.meta.jdbc.JdbcBuildFileSource;
import org.junit.Before;
import org.junit.Test;
import java.sql.Driver;
import java.util.Arrays;
import java.util.Collections;

/**
 * 构建总线测试
 *
 * @author peng_fu_lin
 * 2022-09-05 11:19
 */
public class TestModel {

    JdbcBuildFileSource jdbcBuildFileSource=null;


//    @Before
    public void before(){
        //数据源
        jdbcBuildFileSource = new JdbcBuildFileSource();
//        jdbcBuildFileSource.setUrl("jdbc:mysql://130.120.2.219:3306");
//        jdbcBuildFileSource.setDataBaseName("fullres_test_prod");
//        jdbcBuildFileSource.setTableNames(Collections.singletonList("sharing_special"));
//        jdbcBuildFileSource.setUserName("fullres");
//        jdbcBuildFileSource.setPassword("123456");
//        jdbcBuildFileSource.setDriverClass(Driver.class);
        jdbcBuildFileSource.setUrl("jdbc:mysql://130.120.3.158:3306/");
        jdbcBuildFileSource.setDataBaseName("gxts");
        jdbcBuildFileSource.setTableNames(
                Arrays.asList("process_data_resource_provide_demand", "process_data_resource_provide_demand_audit"));
        jdbcBuildFileSource.setUserName("gxts");
        jdbcBuildFileSource.setPassword("gxts");
        jdbcBuildFileSource.setDriverClass(Driver.class);
    }

    @Before
    public void tduckBefore(){
        jdbcBuildFileSource = new JdbcBuildFileSource();
        jdbcBuildFileSource.setUrl("jdbc:mysql://10.100.100.199:3306/");
        jdbcBuildFileSource.setDataBaseName("tduck_pre");
        jdbcBuildFileSource.setTableNames(
                Collections.singletonList("py_population_from"));
        jdbcBuildFileSource.setUserName("root");
        jdbcBuildFileSource.setPassword("pyzwzx@2022EmS,10");
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
        simpleBuildBus.build(SimpleBeanBuilder.class, jdbcBuildFileSource,null,null);
    }

    @Test
    public void testLombokBeanBuilder() throws BuildBusException {
        //构建总线
        SimpleBuildBus simpleBuildBus = new SimpleBuildBus();
        //执行构建
        simpleBuildBus.build(LombokBeanBuilder.class, jdbcBuildFileSource,null,null);
    }
}