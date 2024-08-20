package builder.core.build.builder.web.controller.mybatis;

import builder.core.build.builder.web.controller.ControllerBuilderProcessor;
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

    /**
     * 构建器创建
     * 2023/9/24 16:21
     * @author pengshuaifeng
     */
    MybatisControllerBuilderProcessor(ControllerBuilderProcessor controllerBuilderProcessor, MybatisServiceBuilderProcessor serviceBuilder){
        this.controllerBuilderProcessor = controllerBuilderProcessor;
        this.serviceBuilderProcessor=serviceBuilder;
    }

    /**
     * 构建
     * 2023/9/24 16:34
     * @author pengshuaifeng
     */
    public void build() {
        serviceBuilderProcessor.build();
        controllerBuilderProcessor.buildByService(serviceBuilderProcessor.getServiceBuilderProcessor().getServiceImpls());
    }
}
