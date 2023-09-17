package mybatis;

import builder.core.build.builder.mybatis.MybatisBuilder;
import builder.model.build.config.path.MybatisPath;
import builder.model.build.config.template.path.MybatisTemplatePath;
import builder.model.build.config.content.orm.MybatisContent;
import builder.model.resolve.database.jdbc.BaseInfo;
import builder.model.resolve.database.jdbc.ConnectionInfo;
import com.mysql.jdbc.Driver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.Collections;

/**
 * mybatis常用模式测试
 * author: pengshuaifeng
 * 2023/9/6
 */
public class MybatisTest {

    private ConnectionInfo connectionInfo;
    private MybatisBuilder mybatisBuilder;
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
        mybatisBuilder.build(mybatisContent);
    }

    //创建构建器 ：默认方式
    @Test
    public void simpleMybatisTest01(){
        mybatisBuilder =MybatisBuilder.builder()
                .connectionInfo(connectionInfo).build();
    }
    //创建构建器 ：设置输出根路径
    @Test
    public void simpleMybatisTest02(){
        mybatisBuilder =MybatisBuilder.builder()
                .connectionInfo(connectionInfo)
                .rootPath("/Users/pengshuaifeng/javaBuilder")
                .build();
    }
    //创建构建器 ：设置组件的构建子路径
    @Test
    public void simpleMybatisTest03(){
        mybatisBuilder =MybatisBuilder.builder()
                .connectionInfo(connectionInfo)
                .rootPath("/Users/pengshuaifeng/javaBuilder")
                .mybatisPath(new MybatisPath("/entities","/mappers","/mappers/xml"))
                .build();
    }
    //创建构建器 ：设置模版路径
    @Test
    public void simpleMybatisTest04(){
        mybatisBuilder =MybatisBuilder.builder()
                .connectionInfo(connectionInfo)
                .rootPath("/Users/pengshuaifeng/works")
                .mybatisPath(new MybatisPath("/entities","/mappers","/mappers/xml"))
                .mybatisTemplatePath(new MybatisTemplatePath("/template/basic/EntityTemplate.txt",
                        "/template/mybatis/simple/MapperTemplate.txt","/template/mybatis/simple/MapperXmlTemplate.txt"))
                .build();
    }
}
