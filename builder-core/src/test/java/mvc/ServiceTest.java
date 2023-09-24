package mvc;


import builder.core.build.builder.mvc.service.ServiceBuilder;
import builder.core.build.builder.mvc.service.mybatis.MybatisServiceBuilder;
import builder.core.build.builder.mvc.service.mybatis.plus.MybatisPlusServiceBuilder;
import builder.core.build.builder.mybatis.MybatisBuilder;
import builder.core.build.builder.mybatis.plus.MybatisPlusBuilder;
import builder.model.build.config.BuildGlobalConfig;
import builder.model.resolve.database.jdbc.BaseInfo;
import builder.model.resolve.database.jdbc.ConnectionInfo;
import com.mysql.jdbc.Driver;
import org.junit.Before;
import org.junit.Test;
import java.util.Collections;

/**
 * 服务构建常用模式测试
 * author: pengshuaifeng
 * 2023/9/23
 */
public class ServiceTest {

    private ConnectionInfo connectionInfo;

    private ServiceBuilder serviceBuilder;

    //创建配置信息
    @Before
    public void before(){
        //设置数据库信息
        connectionInfo=ConnectionInfo.builder()
                .url("jdbc:mysql://192.168.1.103:3307/jpa?useSSL=false&useUnicode=true&characterEncoding=utf-8&autoReconnect=true&allowMultiQueries=true")
                .userName("root")
                .password("root")
                .DriverClass(Driver.class)
                .baseInfo(new BaseInfo("jpa", Collections.singletonList("user"))).build();
        //设置全局构建信息
        BuildGlobalConfig.templateCreateInfo  //模版创建信息
                .setUserName("pengfulin"); //创建用户
    }


    /**
     * mybatis测试
     * 2023/9/23 22:49
     * @author pengshuaifeng
     */
    @Test
    public void test01() {
        //mybatis构建器-负责处理mybatis的构建，如entity、mapper映射器、mapper映射文件
        MybatisBuilder mybatisBuilder = MybatisBuilder.builder()
                .connectionInfo(connectionInfo)
                .rootPath("/Users/pengshuaifeng/javaBuilder")
                .build();
        //service构建器-负责处理service的构建，如service接口、service实现
        serviceBuilder = ServiceBuilder.builder()
                .rootPath("/Users/pengshuaifeng/javaBuilder").build();
        //mybatis-service构建器-负责整合service和mybatis构建器，以集成mybatis和service的功能，是service构建器的具体"实现"！
        MybatisServiceBuilder.builder()
                .mybatisBuilder(mybatisBuilder).serviceBuilder(serviceBuilder).build().build(); //执行构建
    }


    /**
     * mybatis-plus测试
     * 2023/9/23 22:49
     * @author pengshuaifeng
     */
    @Test
    public void test02(){
        MybatisPlusBuilder mybatisPlusBuilder = MybatisPlusBuilder.builder().mybatisBuilder(
                MybatisBuilder.builder().connectionInfo(connectionInfo)
                        .rootPath("/Users/pengshuaifeng/javaBuilder")
                        .build()).build();
        serviceBuilder = ServiceBuilder.builder()
                .rootPath("/Users/pengshuaifeng/javaBuilder").build();
        MybatisPlusServiceBuilder.builder()
                .mybatisPlusBuilder(mybatisPlusBuilder)
                .serviceBuilder(serviceBuilder).build().build();
    }
}
