package common;


import builder.core.build.builder.common.GeneralMybatisBuilderCommon;
import builder.model.build.config.BuildGlobalConfig;
import builder.model.build.config.content.MybatisContent;
import builder.model.build.config.content.WebContent;
import builder.model.resolve.database.jdbc.DataBaseInfo;
import builder.model.resolve.database.jdbc.ConnectionInfo;
import com.mysql.jdbc.Driver;
import org.junit.Before;
import org.junit.Test;
import java.util.Collections;

/**
 * Mybatis-最佳实践
 * author: pengshuaifeng
 * 2023/11/11
 */
public class MybatisCommonTest {

    private GeneralMybatisBuilderCommon builderCommon;

    @Before
    public void before(){
        //设置数据库信息
        ConnectionInfo connectionInfo = ConnectionInfo.builder()
                .url("jdbc:mysql://192.168.1.103:3307/jpa?useSSL=false&useUnicode=true&characterEncoding=utf-8&autoReconnect=true&allowMultiQueries=true")
                .userName("root")
                .password("root")
                .DriverClass(Driver.class)
                .dataBaseInfo(new DataBaseInfo("jpa", Collections.singletonList("user"))).build();
        //设置全局构建信息
        BuildGlobalConfig.templateInfo
                .setUserName("fulin-peng"); //创建用户
        BuildGlobalConfig.templateSuffix="Suffix"; //模板后缀
        BuildGlobalConfig.templatePrefix="Prefix"; //模版前缀
//        BuildGlobalConfig.templateEntity.setJsr303Enable(false);  //不要开启jsr
//        BuildGlobalConfig.templateEntity.setSwaggerEnable(false);  //不要开启swagger
        //创建构建器
        builderCommon= GeneralMybatisBuilderCommon.builder()
                .connectionInfo(connectionInfo)
                .controllerPlusTemplatePath("/template/web/controller/common/practical/MybatisPlusCommonControllerGeneralTemplate.txt") //controller模版自定义
                .build();
    }

    @Test
    public void mybatis(){
        builderCommon.mybatisBuild(MybatisContent.ALL);
    }

    @Test
    public void mybatisPlus(){
        builderCommon.mybatisPlusBuild(MybatisContent.ALL);
    }


    @Test
    public void mybatisService(){
        builderCommon.mybatisWebBuild(WebContent.ALL);
    }

    @Test
    public void mybatisPlusService(){
        builderCommon.mybatisPlusWebBuild(WebContent.ALL);
    }
}
