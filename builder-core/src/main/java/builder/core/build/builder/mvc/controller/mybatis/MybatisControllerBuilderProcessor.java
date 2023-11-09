package builder.core.build.builder.mvc.controller.mybatis;

import builder.core.build.builder.mvc.controller.ControllerBuilderProcessor;
import builder.core.build.builder.mvc.controller.basic.ControllerBuildExecutor;
import builder.core.build.builder.mvc.controller.mybatis.plus.MybatisPlusControllerBuildExecutor;
import builder.core.build.builder.mvc.service.mybatis.MybatisServiceBuilderProcessor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * mybatis控制器构建器
 * author: pengshuaifeng
 * 2023/9/24
 */
@Getter
@Setter
@Builder
public class MybatisControllerBuilderProcessor {

    private ControllerBuilderProcessor controllerBuilderProcessor;
    private MybatisServiceBuilderProcessor serviceBuilder;

    /**
     * 构建器创建
     * 2023/9/24 16:21
     * @author pengshuaifeng
     */
    MybatisControllerBuilderProcessor(ControllerBuilderProcessor controllerBuilderProcessor, MybatisServiceBuilderProcessor serviceBuilder){
        this.controllerBuilderProcessor = controllerBuilderProcessor;
        this.serviceBuilder=serviceBuilder;
        init();
    }

    /**
     * 构建器初始化
     * 2023/9/24 16:22
     * @author pengshuaifeng
     */
    private void init(){
        initBuilder();
    }

    public void initBuilder(){
        if(serviceBuilder.getMybatisPlusBuilderProcessor()!=null){
            initPlusBuilder();
        }else{
            if(controllerBuilderProcessor.getControllerBuildExecutor()==null)
                controllerBuilderProcessor.setControllerBuildExecutor(new ControllerBuildExecutor());
        }
    }

    private void initPlusBuilder(){
        controllerBuilderProcessor.setControllerBuildExecutor(new MybatisPlusControllerBuildExecutor());
    }


    /**
     * 构建
     * 2023/9/24 16:34
     * @author pengshuaifeng
     */
    public void build() {
        serviceBuilder.build();
        controllerBuilderProcessor.setControllers(controllerBuilderProcessor.generateControllers(serviceBuilder.getServiceBuilderProcessor().getServiceImpls()));
        controllerBuilderProcessor.build();
    }
}
