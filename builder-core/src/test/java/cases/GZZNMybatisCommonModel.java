package cases;

import builder.core.build.builder.common.GeneralMybatisBuilderCommon;
import builder.model.build.config.BuildGlobalConfig;
import builder.model.build.config.content.MybatisContent;
import builder.model.build.config.content.WebContent;
import builder.model.resolve.database.jdbc.ConnectionInfo;
import builder.model.resolve.database.jdbc.DataBaseInfo;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Properties;

/**
 * 通用的mybatis构建组件实战案例-广州智能
 *
 * @author fulin-peng
 * 2023-11-01  14:37
 */
public class GZZNMybatisCommonModel {

    private GeneralMybatisBuilderCommon builderCommon;

    @Before
    public void before() throws Exception {
        //设置数据库信息
        Properties properties = new Properties();
        properties.load(GZZNMybatisCommonModel.class.getResourceAsStream("/BuilderConfig.properties"));
        ConnectionInfo connectionInfo =ConnectionInfo.builder()
                .url(properties.get("jdbc.url").toString())
                .userName(properties.get("jdbc.userName").toString())
                .password(properties.get("jdbc.password").toString())
                .DriverClass((Class<? extends java.sql.Driver>) Class.forName(properties.get("jdbc.driverClass").toString()))
                .dataBaseInfo(new DataBaseInfo(properties.get("jdbc.dataBase.name").toString(), Arrays.asList(properties.get("jdbc.dataBase.tables").toString().split(",")))).build();
        //设置全局构建信息
        BuildGlobalConfig.templateCreateInfo
                .setUserName("fulin-peng"); //创建用户
        //创建构建器
        builderCommon= GeneralMybatisBuilderCommon.builder()
                .connectionInfo(connectionInfo)
                .rootPath(properties.get("path.rootPath").toString())
                .servicePath(properties.get("path.servicePath").toString())
                .serviceImplPath(properties.get("path.serviceImplPath").toString())
                .entityPath(properties.get("path.entityPath").toString())
                .mapperPath(properties.get("path.mapperPath").toString())
                .mapperXmlPath(properties.get("path.mapperXmlPath").toString())
                .build();
    }

    @Test
    public void mybatisPlusService(){
        builderCommon.mybatisPlusWebBuild(WebContent.SERVICE);
    }

    @Test
    public void mybatisPlusEntity(){
        builderCommon.mybatisPlusBuild(MybatisContent.ENTITY);
    }
}
