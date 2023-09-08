package mybatis;

import builder.core.build.builder.mybatis.MybatisBuilder;
import builder.core.build.builder.mybatis.SimpleMybatisBuilder;
import builder.model.build.config.path.MybatisPath;
import builder.model.build.config.template.path.MybatisTemplatePath;
import builder.model.build.config.type.MybatisType;
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
    private MybatisType mybatisType;

    //创建配置信息
    @Before
    public void before(){
        //设置数据库信息
        connectionInfo = new ConnectionInfo();
        connectionInfo.setUrl("jdbc:mysql://192.168.1.103:3307/jpa?useSSL=false&useUnicode=true&characterEncoding=utf-8&autoReconnect=true&allowMultiQueries=true");
        connectionInfo.setUserName("root");
        connectionInfo.setPassword("root");
        connectionInfo.setDriverClass(Driver.class);
        connectionInfo.setBaseInfo(new BaseInfo("jpa", Collections.singletonList("user")));
        //设置构建模式：一般选择全部ALL（构建实体、映射器和xml）或ENTITY（仅构建实体）
        mybatisType=MybatisType.ALL;
    }

    //执行构建
    @After
    public void after(){
        mybatisBuilder.build(mybatisType);
    }

    //创建构建器 ：默认方式
    @Test
    public void simpleMybatisTest01(){
        mybatisBuilder = new SimpleMybatisBuilder(connectionInfo, "");
    }
    //创建构建器 ：设置输出根路径
    @Test
    public void simpleMybatisTest02(){
        mybatisBuilder = new SimpleMybatisBuilder(connectionInfo, "/Users/pengshuaifeng");
    }
    //创建构建器 ：设置组件的构建子路径
    @Test
    public void simpleMybatisTest03(){
        MybatisPath mybatisPath = new MybatisPath();
        mybatisPath.setEntity("/entity");
        mybatisPath.setMapper("/mapper");
        mybatisPath.setMapperXml("/mapper/xml");
        mybatisPath.setRoot("/Users/pengshuaifeng/javaBuilder");
        mybatisBuilder = new SimpleMybatisBuilder(connectionInfo, mybatisPath);
    }
    //创建构建器 ：设置模版路径
    @Test
    public void simpleMybatisTest04(){
        MybatisTemplatePath mybatisTemplatePath = new MybatisTemplatePath();
        mybatisTemplatePath.setEntityPath("/template/basic/EntityTemplate.txt");
        mybatisTemplatePath.setMapperPath("/template/mybatis/simple/MapperTemplate.txt");
        mybatisTemplatePath.setMapperXmlPath("/template/mybatis/simple/MapperXmlTemplate.txt");
        mybatisBuilder = new SimpleMybatisBuilder(connectionInfo,"/Users/pengshuaifeng/javaBuilder",mybatisTemplatePath);
    }
}
