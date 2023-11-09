package mybatis;

import builder.core.build.builder.mybatis.MybatisBuilderProcessor;
import builder.core.build.builder.mybatis.plus.MybatisPlusBuilderProcessor;
import builder.model.build.config.content.MybatisContent;
import builder.model.resolve.database.jdbc.BaseInfo;
import builder.model.resolve.database.jdbc.ConnectionInfo;
import com.mysql.jdbc.Driver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.Collections;
/**
 * mybatis-plus测试
 * author: pengshuaifeng
 * 2023/9/18
 */
public class MybatisPlusTest{

    private ConnectionInfo connectionInfo;
    private MybatisPlusBuilderProcessor mybatisPlusBuilderProcessor;
    private MybatisContent mybatisContent;

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
        //设置构建模式：一般选择全部ALL（构建实体、映射器和xml）或ENTITY（仅构建实体）
        mybatisContent = MybatisContent.ALL;
    }

    //执行构建
    @After
    public void after(){
        mybatisPlusBuilderProcessor.build(mybatisContent);
    }

    //创建构建器：默认方式
    @Test
    public void test1(){
        mybatisPlusBuilderProcessor = MybatisPlusBuilderProcessor.builder().mybatisBuilderProcessor(
                MybatisBuilderProcessor.builder().connectionInfo(connectionInfo)
                        .rootPath("/Users/pengshuaifeng/javaBuilder")
                        .build()).build();
    }
}
