package builder.core.build.builder.mvc.controller;


import builder.core.build.builder.mvc.controller.basic.ControllerBuildExecutor;
import builder.core.build.response.FileResponder;
import builder.core.build.response.Responder;
import builder.model.build.config.template.Template;
import builder.model.build.mvc.Controller;
import builder.model.build.mvc.service.Service;
import builder.model.build.orm.Entity;
import builder.util.ClassUtil;
import builder.util.CollectionUtil;
import builder.util.StringUtil;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 控制器构建器
 * author: pengshuaifeng
 * 2023/9/24
 */
@Getter
@Setter
@Builder
public class ControllerBuilderProcessor {

    //数据源
    private List<Controller> controllers;
    //构建器
    private ControllerBuildExecutor controllerBuildExecutor;
    //构建路径
    private String rootPath;
    private String executePath;
    //构建响应器
    private Responder responder;

    /**
     * 构建器创建
     * 2023/9/24 15:22
     * @author pengshuaifeng
     */
    ControllerBuilderProcessor(List<Controller> controllers, ControllerBuildExecutor controllerBuildExecutor, String rootPath, String executePath, Responder responder){
        this.controllers=controllers;
        this.controllerBuildExecutor = controllerBuildExecutor;
        this.rootPath=rootPath;
        this.executePath=executePath;
        this.responder=responder;
        init();
    }

    /**
     * 构建器初始化
     * 2023/9/24 15:23
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
        executePath= StringUtil.isNotEmpty(executePath)?executePath:"java"+ File.separator+"controller";
    }

    public void initBuildResponder(){
        if(responder==null){
            responder=new FileResponder();
        }
        if(StringUtil.isNotEmpty(rootPath))
            responder.setRootPath(rootPath);
    }

    public void initBuilder(){
        controllerBuildExecutor = controllerBuildExecutor ==null?new ControllerBuildExecutor(): controllerBuildExecutor;
    }

    /**
     * 构建
     * 2023/9/24 15:34
     * @author pengshuaifeng
     */
    public void build(){
        if(CollectionUtil.isNotEmpty(controllers)){
            controllers.forEach(this::buildExecute);
        }else throw new RuntimeException("没有构建源");
    }

    /**
     * 构建执行
     * 2023/9/24 15:38
     * @author pengshuaifeng
     */
    private void buildExecute(Controller controller){
        String executeValue = buildExecuteValue(controller);
        responder.execute(executeValue,controller.getName()+".java",executePath);
    }

    private String buildExecuteValue(Controller controller){
        return controllerBuildExecutor.build(controller);
    }

    /**
     * 生成控制器
     * 2023/9/24 16:04
     * @author pengshuaifeng
     */
    public Controller generateController(Service serviceImpl){
        Controller controller = new Controller();
        Entity entity = serviceImpl.getEntity();
        controller.setName(entity.getName()+"Controller");
        controller.setDescription(entity.getName()+"控制器");
        controller.setEntity(entity);
        controller.setServiceImpl(serviceImpl);
        String referencePath = ClassUtil.generateReferencePath(executePath);
        controller.setPackages(referencePath);
        controller.setReference(referencePath +"."+controller.getName());
        return controller;
    }

    public List<Controller> generateControllers(List<Service> serviceImpls){
        List<Controller> result=new LinkedList<>();
        for (Service serviceImpl : serviceImpls) {
            Controller controller = generateController(serviceImpl);
            result.add(controller);
        }
        return result;
    }

   /**
    * 基础控制器模版填充
    * 2023/9/24 17:22
    * @author pengshuaifeng
    */
    public static Map<String,String> buildBasicPadding(Controller controller,Template serviceTemplate){
        Map<String, String> paddings = new HashMap<>();
        paddings.put("{package}", controller.getReference());
        paddings.put("{description}", controller.getDescription());
        Service serviceInterface = controller.getServiceImpl().getServiceInterface();
        paddings.put("{Service}", serviceInterface.getName());
        paddings.put("{service}",ClassUtil.nameToAttribute(serviceInterface.getName()));
        Entity entity = controller.getEntity();
        paddings.put("{Entity}", entity.getName());
        paddings.put("{entity}",ClassUtil.nameToAttribute(entity.getName()));
        return paddings;
    }
}
