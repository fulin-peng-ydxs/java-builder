package cases.gzzn;

import builder.core.build.builder.web.service.ServiceBuilderProcessor;
import builder.core.build.builder.web.service.mybatis.MybatisServiceBuilderProcessor;
import builder.core.build.builder.mybatis.MybatisBuilderProcessor;
import builder.core.build.builder.mybatis.plus.MybatisPlusBuilderProcessor;
import builder.model.build.config.BuildGlobalConfig;
import builder.model.resolve.database.jdbc.DataBaseInfo;
import builder.model.resolve.database.jdbc.ConnectionInfo;
import com.mysql.jdbc.Driver;
import org.junit.Before;
import org.junit.Test;
import java.util.Arrays;

/**
 * mybatis实战案例-广州智能
 *
 * @author fulin-peng
 * 2023-11-01  14:37
 */
public class GZZNMybatisModelCase {

    private ConnectionInfo connectionInfo;

    private ServiceBuilderProcessor serviceBuilderProcessor;

    //创建配置信息
    @Before
    public void before(){
        //设置数据库信息
        connectionInfo=ConnectionInfo.builder()
                .url("jdbc:mysql://10.100.100.95:3307/fullres?useSSL=false&useUnicode=true&characterEncoding=utf-8&autoReconnect=true&allowMultiQueries=true")
                .userName("fullres")
                .password("fuLL@2023")
                .DriverClass(Driver.class)
//                .dataBaseInfo(new DataBaseInfo("gxts", Collections.singletonList("process_notify_manage"))).build();
                .dataBaseInfo(new DataBaseInfo("fullres",
                        Arrays.asList("process_notify_business","process_notify_business_impl","process_notify_business_message"))).build();
        //设置全局构建信息
        BuildGlobalConfig.templateInfo  //模版创建信息
                .setUserName("fulin-peng"); //创建用户
        //设置公共构建器
        serviceBuilderProcessor = ServiceBuilderProcessor.builder()
                .rootPath("E:\\flowabletask-test\\flowable-task\\src\\main")
                .serviceInterfacePath("\\java\\com\\gzz\\gxts\\flowableTask\\service\\ext\\process\\notify")
                .serviceImplPath("\\java\\com\\gzz\\gxts\\flowableTask\\service\\ext\\process\\notify\\impl")
                .build();
    }

    @Test
    public void test(){
        //mybatis层
        MybatisPlusBuilderProcessor mybatisPlusBuilderProcessor = MybatisPlusBuilderProcessor.builder().mybatisBuilderProcessor(
                MybatisBuilderProcessor.builder().connectionInfo(connectionInfo)
                        .rootPath("E:\\flowabletask-test\\flowable-task\\src\\main")
                        .entityPath("\\java\\com\\gzz\\gxts\\flowableTask\\business\\model\\ext\\process\\notify")
                        .mapperPath("\\java\\com\\gzz\\gxts\\flowableTask\\mapper\\gxts")
                        .mapperXmlPath("\\resources\\business\\mapping\\ext\\process\\notify")
                        .build()).build();
        //service层
        MybatisServiceBuilderProcessor.builder()
                .mybatisPlusBuilderProcessor(mybatisPlusBuilderProcessor).serviceBuilderProcessor(serviceBuilderProcessor).build().build(); //执行构建
    }
}
