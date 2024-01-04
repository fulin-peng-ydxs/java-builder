package cases;

import builder.core.build.builder.web.service.ServiceBuilderProcessor;
import builder.core.build.builder.web.service.mybatis.MybatisServiceBuilderProcessor;
import builder.core.build.builder.mybatis.MybatisBuilderProcessor;
import builder.core.build.builder.mybatis.plus.MybatisPlusBuilderProcessor;
import builder.model.build.config.BuildGlobalConfig;
import builder.model.resolve.database.jdbc.BaseInfo;
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
public class GZZNMybatisModel {

    private ConnectionInfo connectionInfo;

    private ServiceBuilderProcessor serviceBuilderProcessor;

    //创建配置信息
    @Before
    public void before(){
        //设置数据库信息
        connectionInfo=ConnectionInfo.builder()
                .url("jdbc:mysql://130.120.3.158:3306/gxts?useSSL=false&useUnicode=true&characterEncoding=utf-8&autoReconnect=true&allowMultiQueries=true")
                .userName("gxts")
                .password("gxts")
                .DriverClass(Driver.class)
//                .baseInfo(new BaseInfo("gxts", Collections.singletonList("process_notify_manage"))).build();
                .baseInfo(new BaseInfo("gxts",
                        Arrays.asList("process_notify_business","process_notify_business_impl","process_notify_business_message"))).build();
        //设置全局构建信息
        BuildGlobalConfig.templateCreateInfo  //模版创建信息
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
