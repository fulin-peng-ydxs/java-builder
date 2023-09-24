package builder.core.build.builder.mvc.controller.mybatis;

import builder.core.build.builder.mvc.controller.ControllerBuilder;
import builder.core.build.builder.mvc.controller.basic.ControllerBuildExecutor;
import builder.core.build.builder.mvc.controller.mybatis.plus.MybatisPlusControllerBuildExecutor;
import builder.core.build.builder.mvc.service.mybatis.MybatisServiceBuilder;
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
public class MybatisControllerBuilder{

    private ControllerBuilder controllerBuilder;
    private MybatisServiceBuilder serviceBuilder;

    /**
     * 构建器创建
     * 2023/9/24 16:21
     * @author pengshuaifeng
     */
    MybatisControllerBuilder(ControllerBuilder controllerBuilder,MybatisServiceBuilder serviceBuilder){
        this.controllerBuilder=controllerBuilder;
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
        if(serviceBuilder.getMybatisPlusBuilder()!=null){
            initPlusBuilder();
        }else{
            if(controllerBuilder.getControllerBuildExecutor()==null)
                controllerBuilder.setControllerBuildExecutor(new ControllerBuildExecutor());
        }
    }

    private void initPlusBuilder(){
        controllerBuilder.setControllerBuildExecutor(new MybatisPlusControllerBuildExecutor());
    }


    /**
     * 构建
     * 2023/9/24 16:34
     * @author pengshuaifeng
     */
    public void build() {
        serviceBuilder.build();
        controllerBuilder.setControllers(controllerBuilder.generateControllers(serviceBuilder.getServiceBuilder().getServiceImpls()));
        controllerBuilder.build();
    }
}
