package cases;

import builder.core.build.builder.common.GeneralMybatisBuilderCommon;
import builder.model.build.config.BuildGlobalConfig;
import builder.model.build.config.content.MybatisContent;
import builder.model.build.config.content.WebContent;
import builder.model.resolve.database.jdbc.ConnectionInfo;
import builder.model.resolve.database.jdbc.DataBaseInfo;
import com.mysql.jdbc.Driver;
import org.junit.Before;
import org.junit.Test;
import java.util.Collections;

/**
 * 通用的mybatis构建组件实战案例-广州智能
 *
 * @author fulin-peng
 * 2023-11-01  14:37
 */
public class GZZNMybatisCommonModel {

    private GeneralMybatisBuilderCommon builderCommon;

    @Before
    public void before(){
//        //设置数据库信息
//        ConnectionInfo connectionInfo = ConnectionInfo.builder()
//                .url("jdbc:mysql://130.120.3.158:3306/gxts?useSSL=false&useUnicode=true&characterEncoding=utf-8&autoReconnect=true&allowMultiQueries=true")
//                .userName("gxts")
//                .password("gxts")
//                .DriverClass(Driver.class)
//                .dataBaseInfo(new DataBaseInfo("gxts", Arrays.asList("process_data_resource_obtain_impl"))).build();
        ConnectionInfo connectionInfo =ConnectionInfo.builder()
                .url("jdbc:mysql://10.100.100.95:3307/fullres?useSSL=false&useUnicode=true&characterEncoding=utf-8&autoReconnect=true&allowMultiQueries=true")
                .userName("fullres")
                .password("fuLL@2023")
                .DriverClass(Driver.class)
                .dataBaseInfo(new DataBaseInfo("fullres", Collections.singletonList("resource_info_error_log"))).build();
        //设置全局构建信息
        BuildGlobalConfig.templateCreateInfo
                .setUserName("fulin-peng"); //创建用户
        //创建构建器
        builderCommon= GeneralMybatisBuilderCommon.builder()
                .connectionInfo(connectionInfo)
                .rootPath("E:\\flowabletask-test\\flowable-task\\src\\main")
                .servicePath("\\java\\com\\gzz\\gxts\\flowableTask\\service\\fullres")
                .serviceImplPath("\\java\\com\\gzz\\gxts\\flowableTask\\service\\fullres\\impl")
                .entityPath("\\java\\com\\gzz\\gxts\\flowableTask\\business\\model\\fullres")
                .mapperPath("\\java\\com\\gzz\\gxts\\flowableTask\\mapper\\fullres")
                .mapperXmlPath("\\resources\\business\\mapping-fullres")
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
