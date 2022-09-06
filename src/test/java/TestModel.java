import build.source.data.BuildMeta;
import build.source.resolver.exception.BuildSourceResolverException;
import build.source.resolver.jdbc.JdbcBuildSourceResolver;
import build.source.meta.jdbc.JdbcBuildFileSource;
import com.mysql.jdbc.Driver;
import org.junit.Test;

import java.util.Collections;
import java.util.Map;

/**
 * 构建总线测试
 *
 * @author peng_fu_lin
 * 2022-09-05 11:19
 */
public class TestModel {


    @Test
    public void testBus(){

    }

    @Test
    public void testJdbcBuildResolver() throws BuildSourceResolverException {
        JdbcBuildSourceResolver jdbcBuildSourceResolver = new JdbcBuildSourceResolver();
        JdbcBuildFileSource jdbcBuildFileSource = new JdbcBuildFileSource();
        jdbcBuildFileSource.setPassword("123456");
        jdbcBuildFileSource.setUserName("fullres");
        jdbcBuildFileSource.setUrl("jdbc:mysql://130.120.2.219:3306/fullres_test_prod");
        jdbcBuildFileSource.setTableNames(Collections.singletonList("resource_list"));
        jdbcBuildFileSource.setDriverClass(Driver.class);
        jdbcBuildFileSource.setDataBaseName("fullres_test_prod");
        Map<String, BuildMeta> resolver = jdbcBuildSourceResolver.resolver(jdbcBuildFileSource);
        System.out.println(resolver);
    }
}