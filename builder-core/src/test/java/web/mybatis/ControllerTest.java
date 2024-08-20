package web.mybatis;


import builder.core.build.builder.mybatis.MybatisBuilderProcessor;
import builder.core.build.builder.mybatis.plus.MybatisPlusBuilderProcessor;
import builder.core.build.builder.web.controller.ControllerBuilderProcessor;
import builder.core.build.builder.web.controller.mybatis.MybatisControllerBuilderProcessor;
import builder.core.build.builder.web.controller.mybatis.plus.MybatisPlusControllerBuildExecutor;
import builder.core.build.builder.web.service.ServiceBuilderProcessor;
import builder.core.build.builder.web.service.mybatis.MybatisServiceBuilderProcessor;
import builder.model.build.config.BuildGlobalConfig;
import builder.model.resolve.database.jdbc.ConnectionInfo;
import builder.model.resolve.database.jdbc.DataBaseInfo;
import com.mysql.jdbc.Driver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

/**
 * 控制器构建测试
 * author: pengshuaifeng
 * 2023/9/24
 */
public class ControllerTest {

    private ConnectionInfo connectionInfo;

    private ControllerBuilderProcessor controllerBuilderProcessor;
    private ServiceBuilderProcessor serviceBuilderProcessor;
    private MybatisServiceBuilderProcessor mybatisServiceBuilderProcessor;

    //创建配置信息
    @Before
    public void before(){
        //设置数据库信息
        connectionInfo= ConnectionInfo.builder()
                .url("jdbc:mysql://192.168.1.103:3307/jpa?useSSL=false&useUnicode=true&characterEncoding=utf-8&autoReconnect=true&allowMultiQueries=true")
                .userName("root")
                .password("root")
                .DriverClass(Driver.class)
                .dataBaseInfo(new DataBaseInfo("jpa", Collections.singletonList("user"))).build();
        //设置全局构建信息
        BuildGlobalConfig.templateInfo  //模版创建信息
                .setUserName("pengfulin"); //创建用户
        //设置公共构建器
        serviceBuilderProcessor = ServiceBuilderProcessor.builder()
                .rootPath("/Users/pengshuaifeng/javaBuilder").build();
        controllerBuilderProcessor = ControllerBuilderProcessor.builder()
                .rootPath("/Users/pengshuaifeng/javaBuilder").build();
    }

    //构建
    @After
    public void after(){
        MybatisControllerBuilderProcessor.builder()
                .controllerBuilderProcessor(controllerBuilderProcessor)
                .serviceBuilderProcessor(mybatisServiceBuilderProcessor).build().build();
    }


    /**
     * mybatis测试
     * 2023/9/24 17:56
     * @author pengshuaifeng
     */
    @Test
    public void test01(){
        //mybatis层
        MybatisBuilderProcessor mybatisBuilderProcessor = MybatisBuilderProcessor.builder()
                .connectionInfo(connectionInfo)
                .rootPath("/Users/pengshuaifeng/javaBuilder")
                .build();
        //service层
         mybatisServiceBuilderProcessor = MybatisServiceBuilderProcessor.builder()
                .mybatisBuilderProcessor(mybatisBuilderProcessor).serviceBuilderProcessor(serviceBuilderProcessor).build();

    }

    /**
     * mybatis-plus测试
     * 2023/10/3 19:59
     * @author pengshuaifeng
     */
    @Test
    public void test02(){
        //mybatis层
        MybatisPlusBuilderProcessor mybatisPlusBuilderProcessor = MybatisPlusBuilderProcessor.builder().mybatisBuilderProcessor(
                MybatisBuilderProcessor.builder().connectionInfo(connectionInfo)
                        .rootPath("/Users/pengshuaifeng/javaBuilder")
                        .build()).build();
        //service层
        mybatisServiceBuilderProcessor = MybatisServiceBuilderProcessor.builder()
                .mybatisPlusBuilderProcessor(mybatisPlusBuilderProcessor).serviceBuilderProcessor(serviceBuilderProcessor).build();
        controllerBuilderProcessor.setControllerBuildExecutor(MybatisPlusControllerBuildExecutor.INSTANCE);
    }

}
