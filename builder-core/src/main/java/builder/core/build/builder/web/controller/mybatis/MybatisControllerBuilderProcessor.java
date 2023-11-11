package builder.core.build.builder.web.controller.mybatis;

import builder.core.build.builder.web.controller.ControllerBuilderProcessor;
import builder.core.build.builder.web.controller.mybatis.plus.MybatisPlusControllerBuildExecutor;
import builder.core.build.builder.web.service.mybatis.MybatisServiceBuilderProcessor;
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
    private MybatisServiceBuilderProcessor serviceBuilderProcessor;

    private boolean ignoreInitControllerExecutor;

    /**
     * 构建器创建
     * 2023/9/24 16:21
     * @author pengshuaifeng
     */
    MybatisControllerBuilderProcessor(ControllerBuilderProcessor controllerBuilderProcessor, MybatisServiceBuilderProcessor serviceBuilder,
                                      boolean ignoreInitControllerExecutor){
        this.controllerBuilderProcessor = controllerBuilderProcessor;
        this.serviceBuilderProcessor=serviceBuilder;
        this.ignoreInitControllerExecutor=ignoreInitControllerExecutor;
        init();
    }

    /**
     * 构建器初始化
     * 2023/9/24 16:22
     * @author pengshuaifeng
     */
    private void init(){
        initBuildExecutor();
    }

    public void initBuildExecutor(){
        if(!ignoreInitControllerExecutor){
            if(serviceBuilderProcessor.getMybatisPlusBuilderProcessor()!=null)  //使用mybatis-plus模型
                controllerBuilderProcessor.setControllerBuildExecutor(new MybatisPlusControllerBuildExecutor());
        }
    }

    /**
     * 构建
     * 2023/9/24 16:34
     * @author pengshuaifeng
     */
    public void build() {
        serviceBuilderProcessor.build();
        controllerBuilderProcessor.setControllers(controllerBuilderProcessor.generateControllers(serviceBuilderProcessor.getServiceBuilderProcessor().getServiceImpls()));
        controllerBuilderProcessor.build();
    }
}
