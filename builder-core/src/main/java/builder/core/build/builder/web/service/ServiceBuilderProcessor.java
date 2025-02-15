package builder.core.build.builder.web.service;

import builder.core.build.builder.web.service.baic.ServiceImplBuilder;
import builder.core.build.builder.web.service.baic.ServiceInterfaceBuilder;
import builder.core.build.response.FileResponder;
import builder.core.build.response.Responder;
import builder.model.build.config.BuildGlobalConfig;
import builder.model.build.config.template.basic.Template;
import builder.model.build.orm.entity.Entity;
import builder.model.build.web.service.Service;
import builder.util.ClassUtils;
import builder.util.CollectionUtils;
import builder.util.FileUtils;
import builder.util.StringUtils;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 服务构建器
 * author: pengshuaifeng
 * 2023/9/12
 */
@Slf4j
@Setter
@Getter
@Builder
public class ServiceBuilderProcessor {

    //数据源
    private Collection<Service> serviceImpls;
    private Collection<Service> serviceInterfaces;
    //构建器
    private ServiceImplBuilder serviceImplBuilder;
    private ServiceInterfaceBuilder serviceInterfaceBuilder;
    //构建路径
    private String rootPath;
    private String serviceImplPath;
    private String serviceInterfacePath;
    //构建响应器
    private Responder responder;

    /**
     * 构建器创建
     * 2023/9/23 10:58
     * @author pengshuaifeng
     */
    ServiceBuilderProcessor(Collection<Service> serviceInterfaces, Collection<Service> serviceImpls, ServiceImplBuilder serviceImplBuilder, ServiceInterfaceBuilder serviceInterfaceBuilder, String rootPath, String serviceImplPath, String serviceInterfacePath, Responder responder) {
        this.serviceImpls=serviceImpls;
        this.serviceInterfaces=serviceInterfaces;
        this.serviceImplBuilder=serviceImplBuilder;
        this.serviceInterfaceBuilder=serviceInterfaceBuilder;
        this.rootPath = rootPath;
        this.serviceImplPath = serviceImplPath;
        this.serviceInterfacePath = serviceInterfacePath;
        this.responder = responder;
        init();
    }

    /**
     * 构建器初始化
     * 2023/9/21 22:51
     * @author pengshuaifeng
     */
    private void init(){
        //初始化路径
        initBuildPath();
        //初始化构建器
        initBuilder();
        //初始化响应器
        initBuildResponder();
    }

    public void initBuildPath(){
        serviceInterfacePath= StringUtils.isNotEmpty(serviceInterfacePath)?serviceInterfacePath:"java"+File.separator+"service";
        serviceImplPath= StringUtils.isNotEmpty(serviceImplPath)?serviceImplPath:"java"+ File.separator+"service"+File.separator+"impl";
    }

    public void initBuildResponder(){ //TODO 应该加入processor抽象类统一处理响应器初始化
        if(responder==null){
            responder=new FileResponder();
        }
        if(StringUtils.isNotEmpty(rootPath))
            responder.setRootPath(rootPath);
    }

    public void initBuilder(){
        serviceInterfaceBuilder=serviceInterfaceBuilder==null? ServiceInterfaceBuilder.INSTANCE:serviceInterfaceBuilder;
        serviceImplBuilder=serviceImplBuilder==null? ServiceImplBuilder.INSTANCE:serviceImplBuilder;
    }

    /**
     * 构建
     * 2023/9/23 12:57
     * @author pengshuaifeng
     */
    public void build(){
        if(!BuildGlobalConfig.templateWeb.isServiceEnable()){
            log.debug("“服务”构建器不参与构建：isServiceEnable={}", false);
            return;
        }
        if(CollectionUtils.isNotEmpty(serviceImpls)  && CollectionUtils.isNotEmpty(serviceInterfaces)){
            serviceImpls.forEach(this::buildServiceImpl);
            serviceInterfaces.forEach(this::buildServiceInterface);
        }else throw new RuntimeException("没有构建源");
    }

    public void build(Collection<Service> serviceInterfaces,Collection<Service> serviceImpls){
        this.serviceImpls=serviceImpls;
        this.serviceInterfaces=serviceInterfaces;
        build();
    }

    /**
     * 构建服务实现
     * 2023/9/23 13:16
     * @author pengshuaifeng
     */
    private void buildServiceImpl(Service serviceImpl){
        String implValue = buildImplValue(serviceImpl);
        responder.execute(implValue,serviceImpl.getName()+".java",this.serviceImplPath);
    }

    public String buildImplValue(Service serviceImpl){
        return serviceImplBuilder.buildImpl(serviceImpl);
    }


    /**
     * 构建服务接口
     * 2023/9/23 13:16
     * @author pengshuaifeng
     */
    public void buildServiceInterface(Service serviceInterface){
        String interfaceValue = buildInterfaceValue(serviceInterface);
        responder.execute(interfaceValue,serviceInterface.getName()+".java",this.serviceInterfacePath);
    }

    public String buildInterfaceValue(Service serviceInterface){
        return serviceInterfaceBuilder.buildInterface(serviceInterface);
    }


    /**
     * 生成服务接口
     * 2023/9/21 23:34
     * @author pengshuaifeng
     */
    public Service generateServiceInterface(Entity entity){
        Service service = new Service();
        service.setName(entity.getName()+"Service");
        service.setDescription(entity.getDescription()+"服务");
        return generateBaseService(service, ClassUtils.generateReferencePath(FileUtils.pathSeparator(rootPath,serviceInterfacePath)),entity);
    }
    
    /**
     * 生成服务实现
     * 2023/9/21 23:36
     * @author pengshuaifeng
     */
    public Service generateServiceImpl(Entity entity,Service serviceInterface){
        Service service = new Service();
        service.setName(entity.getName()+"ServiceImpl");
        service.setDescription(entity.getDescription()+"服务实现");
        service.setServiceInterface(serviceInterface);
        return generateBaseService(service, ClassUtils.generateReferencePath(FileUtils.pathSeparator(rootPath,serviceImplPath)) ,entity);
    }

    private Service generateBaseService(Service service,String referencePath,Entity entity){
        service.setEntity(entity);
        service.setPackages(referencePath);
        service.setReference(referencePath +"."+service.getName());
        return service;
    }

    /**
     * 基础服务模版填充
     * 2023/9/12 23:10
     * @author pengshuaifeng
     */
    public static Map<String,String> buildBasicPadding(Service service ,Template serviceTemplate){
        Map<String, String> paddings = new HashMap<>();
        paddings.put("{package}", service.getPackages());
        paddings.put("{description}", service.getDescription());
        paddings.put("{Service}", service.getName());
        Entity entity = service.getEntity();
        paddings.put("{Entity}", entity.getName());
        paddings.put("{entity}", ClassUtils.nameToAttribute(entity.getName()));
        return paddings;
    }
}
