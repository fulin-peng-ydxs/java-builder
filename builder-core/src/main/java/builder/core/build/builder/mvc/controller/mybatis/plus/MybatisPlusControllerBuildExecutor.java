package builder.core.build.builder.mvc.controller.mybatis.plus;


import builder.core.build.builder.mvc.controller.basic.ControllerBuildExecutor;

/**
 * mybatis-plus控制器构建执行器
 * author: pengshuaifeng
 * 2023/9/24
 */
public class MybatisPlusControllerBuildExecutor extends ControllerBuildExecutor {

    public MybatisPlusControllerBuildExecutor(){
        this("/template/mvc/controller/mybatis/plus/MybatisPlusControllerTemplate.txt");
    }
    public MybatisPlusControllerBuildExecutor(String templatePath){
        super(templatePath);
    }
}
