package use;

import builder.core.build.builder.common.MybatisBuilderCommon;
import builder.model.build.config.BuildGlobalConfig;
import builder.model.build.config.content.MybatisContent;
import builder.model.build.config.content.WebContent;
import builder.model.resolve.database.jdbc.BaseInfo;
import builder.model.resolve.database.jdbc.ConnectionInfo;
import com.mysql.jdbc.Driver;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

/**
 * mybatis常用构建组件实战案例-广州智能
 *
 * @author fulin-peng
 * 2023-11-01  14:37
 */
public class UseGZZNMybatisCommonModel {

    private MybatisBuilderCommon builderCommon;

    @Before
    public void before(){
        //设置数据库信息
        ConnectionInfo connectionInfo = ConnectionInfo.builder()
                .url("jdbc:mysql://130.120.3.158:3306/gxts?useSSL=false&useUnicode=true&characterEncoding=utf-8&autoReconnect=true&allowMultiQueries=true")
                .userName("gxts")
                .password("gxts")
                .DriverClass(Driver.class)
                .baseInfo(new BaseInfo("gxts", Collections.singletonList("act_ru_task"))).build();
        //设置全局构建信息
        BuildGlobalConfig.templateCreateInfo
                .setUserName("fulin-peng"); //创建用户
        //创建构建器
        builderCommon=MybatisBuilderCommon.builder()
                .connectionInfo(connectionInfo)
                .rootPath("E:\\flowabletask-test\\flowable-task\\src\\main")
                .servicePath("\\java\\com\\gzz\\gxts\\flowableTask\\service")
                .serviceImplPath("\\java\\com\\gzz\\gxts\\flowableTask\\service\\impl")
                .entityPath("\\java\\com\\gzz\\gxts\\flowableTask\\business\\model")
                .mapperPath("\\java\\com\\gzz\\gxts\\flowableTask\\mapper\\gxts")
                .mapperXmlPath("\\resources\\business\\mapping")
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
