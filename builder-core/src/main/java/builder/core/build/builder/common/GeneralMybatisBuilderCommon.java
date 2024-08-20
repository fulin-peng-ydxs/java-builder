package builder.core.build.builder.common;

import builder.core.build.builder.entity.EntityBuilder;
import builder.core.build.builder.entity.base.conf.DefaultEntityBuilder;
import builder.core.build.builder.entity.mybatis.MybatisPlusEntityBuilder;
import builder.core.build.builder.mybatis.MybatisBuilderProcessor;
import builder.core.build.builder.mybatis.plus.MybatisPlusBuilderProcessor;
import builder.core.build.builder.web.controller.ControllerBuilderProcessor;
import builder.core.build.builder.web.controller.basic.ControllerBuildExecutor;
import builder.core.build.builder.web.controller.basic.JSR303ControllerBuildExecutor;
import builder.core.build.builder.web.controller.basic.SwaggerControllerBuildExecutor;
import builder.core.build.builder.web.controller.mybatis.MybatisControllerBuilderProcessor;
import builder.core.build.builder.web.service.ServiceBuilderProcessor;
import builder.core.build.builder.web.service.baic.ServiceInterfaceBuilder;
import builder.core.build.builder.web.service.mybatis.MybatisServiceBuilderProcessor;
import builder.core.build.builder.web.service.mybatis.basic.MybatisServiceImplBuilder;
import builder.core.build.builder.web.service.mybatis.plus.MybatisPlusServiceImplBuilder;
import builder.core.build.builder.web.service.mybatis.plus.MybatisPlusServiceInterfaceBuilder;
import builder.model.build.config.BuildGlobalConfig;
import builder.model.build.config.content.MybatisContent;
import builder.model.build.config.content.WebContent;
import builder.model.resolve.database.jdbc.ConnectionInfo;
import lombok.Builder;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 通用的mybatis构建组件
 * author: pengshuaifeng
 * 2023/11/10
 */
@Builder
public class GeneralMybatisBuilderCommon {

    //连接对象
    private ConnectionInfo connectionInfo;

    //构建路径
    private String rootPath;
    private String entityPath;
    private String mapperPath;
    private String mapperXmlPath;
    private String servicePath;
    private String serviceImplPath;
    private String controllerPath;
    //构建模版
    private String controllerTemplatePath;
    private String controllerPlusTemplatePath;
    private  String serviceImplTemplatePath;
    private  String serviceInterfaceTemplatePath;

    /**
     * 常见的mybatis构建
     * 2023/11/10 00:10
     * @author pengshuaifeng
     */
    public void mybatisBuild(MybatisContent mybatisContent){
        MybatisBuilderProcessor mybatisBuilderProcessor = getMybatisBuilderProcessor();
        if (mybatisContent==null) {
            mybatisBuilderProcessor.build();
        }else{
            mybatisBuilderProcessor.build(mybatisContent);
        }
    }

    /**
     * 常见的mybatis-plus构建
     * 2023/11/10 00:10
     * @author pengshuaifeng
     */
    public void mybatisPlusBuild(MybatisContent mybatisContent){
        MybatisPlusBuilderProcessor mybatisPlusBuilderProcessor =getMybatisPlusBuilderProcessor();
        if (mybatisContent==null) {
            mybatisPlusBuilderProcessor.build();
        }else{
            mybatisPlusBuilderProcessor.build(mybatisContent);
        }
    }


    private MybatisBuilderProcessor getMybatisBuilderProcessor(){
        return getMybatisBuilderProcessor("/template/basic/common/CommonEntityTemplate.txt");
    }

    private MybatisBuilderProcessor getMybatisBuilderProcessor(String templatePath){
        EntityBuilder entityBuilder = new EntityBuilder(templatePath,"/template/basic/AnnotationEntityTemplate.txt",
                Stream.of(DefaultEntityBuilder.swagger, DefaultEntityBuilder.jsr303).collect(Collectors.toList()));
        return MybatisBuilderProcessor.builder()
                .connectionInfo(connectionInfo)
                .entityBuilder(entityBuilder)
                .rootPath(rootPath)
                .entityPath(entityPath)
                .mapperPath(mapperPath)
                .mapperXmlPath(mapperXmlPath)
                .build();
    }


    private MybatisPlusBuilderProcessor getMybatisPlusBuilderProcessor(){
        MybatisBuilderProcessor mybatisBuilderProcessor = getMybatisBuilderProcessor(
                "/template/basic/common/CommonMybatisPlusEntityTemplate.txt");
        EntityBuilder entityBuilder = mybatisBuilderProcessor.getEntityBuilder();
        entityBuilder.getEntityBuilders().add(new MybatisPlusEntityBuilder());
        entityBuilder.setIgnorePrimaryKey(true);
       return MybatisPlusBuilderProcessor.builder()
                .mybatisBuilderProcessor(mybatisBuilderProcessor)
                .ignoreInitEntityBuilder(true)
                .build();
    }

    /**
     * 常见的mybatis（web）构建
     * 2023/11/11 00:26
     * @author pengshuaifeng
     */
    public void mybatisWebBuild(WebContent webContent){
        if(webContent==WebContent.SERVICE){
            getMybatisServiceBuilderProcessor().build();
        }else{
            getMybatisControllerBuilderProcessor().build();
        }
    }

    /**
     * 常见的mybatis-plus(web)构建
     * 2023/11/11 00:20
     * @author pengshuaifeng
     */
    public void mybatisPlusWebBuild(WebContent webContent){
        if (webContent== WebContent.ONLY_CONTROLLER || webContent== WebContent.ONLY_SERVICE){
            //关闭实体和映射相关的构建
            BuildGlobalConfig.templateEntity.setBuildEnable(false);
            BuildGlobalConfig.templateMapper.setBuildEnable(false);
            if(webContent== WebContent.ONLY_CONTROLLER )  //关闭服务的构建，仅保留控制器的构建
                BuildGlobalConfig.templateWeb.setServiceEnable(false);
        }
        if(webContent==WebContent.SERVICE){
            getMybatisPlusServiceBuilderProcessor().build();
        }else{
            getMybatisPlusControllerBuilderProcessor().build();
        }
    }

    private MybatisServiceBuilderProcessor getMybatisServiceBuilderProcessor(){
        ServiceBuilderProcessor serviceBuilderProcessor = ServiceBuilderProcessor.builder()
                .rootPath(rootPath)
                .serviceImplPath(serviceImplPath)
                .serviceInterfacePath(servicePath)
                .serviceImplBuilder(serviceImplTemplatePath!=null?new MybatisServiceImplBuilder(serviceImplTemplatePath):MybatisServiceImplBuilder.INSTANCE)
                .serviceInterfaceBuilder(serviceInterfaceTemplatePath!=null?new ServiceInterfaceBuilder(serviceInterfaceTemplatePath):ServiceInterfaceBuilder.INSTANCE)
                .build();
        return MybatisServiceBuilderProcessor.builder()
                .mybatisBuilderProcessor(getMybatisBuilderProcessor()).serviceBuilderProcessor(serviceBuilderProcessor).build();
    }

    private MybatisServiceBuilderProcessor getMybatisPlusServiceBuilderProcessor(){
        ServiceBuilderProcessor serviceBuilderProcessor = ServiceBuilderProcessor.builder()
                .rootPath(rootPath)
                .serviceImplPath(serviceImplPath)
                .serviceInterfacePath(servicePath)
                .serviceImplBuilder(serviceImplTemplatePath!=null?new MybatisPlusServiceImplBuilder(serviceImplTemplatePath):MybatisPlusServiceImplBuilder.INSTANCE)
                .serviceInterfaceBuilder(serviceInterfaceTemplatePath!=null?new MybatisPlusServiceInterfaceBuilder(serviceInterfaceTemplatePath):MybatisPlusServiceInterfaceBuilder.INSTANCE)
                .build();
        return MybatisServiceBuilderProcessor.builder()
                .mybatisPlusBuilderProcessor(getMybatisPlusBuilderProcessor()).serviceBuilderProcessor(serviceBuilderProcessor).build();
    }

    private MybatisControllerBuilderProcessor getMybatisControllerBuilderProcessor(){
        ControllerBuildExecutor controllerBuildExecutor = new ControllerBuildExecutor(controllerTemplatePath==null?
                "/template/web/controller/common/MybatisCommonControllerTemplate.txt":controllerTemplatePath,
                Stream.of(new SwaggerControllerBuildExecutor(), new JSR303ControllerBuildExecutor()).collect(Collectors.toList()));
        ControllerBuilderProcessor controllerBuilderProcessor = ControllerBuilderProcessor.builder()
                .rootPath(rootPath)
                .executePath(controllerPath)
                .controllerBuildExecutor(controllerBuildExecutor)
                .build();
        return MybatisControllerBuilderProcessor.builder()
                .controllerBuilderProcessor(controllerBuilderProcessor)
                .serviceBuilderProcessor(getMybatisServiceBuilderProcessor())
                .build();
    }

    private MybatisControllerBuilderProcessor getMybatisPlusControllerBuilderProcessor(){
        ControllerBuildExecutor controllerBuildExecutor = new ControllerBuildExecutor(controllerPlusTemplatePath==null?
                "/template/web/controller/common/MybatisPlusCommonControllerTemplate.txt":controllerPlusTemplatePath,
                Stream.of(new SwaggerControllerBuildExecutor(), new JSR303ControllerBuildExecutor()).collect(Collectors.toList()));
        ControllerBuilderProcessor controllerBuilderProcessor = ControllerBuilderProcessor.builder()
                .rootPath(rootPath)
                .executePath(controllerPath)
                .controllerBuildExecutor(controllerBuildExecutor)
                .build();
        return MybatisControllerBuilderProcessor.builder()
                .controllerBuilderProcessor(controllerBuilderProcessor)
                .serviceBuilderProcessor(getMybatisPlusServiceBuilderProcessor())
                .build();
    }
}
