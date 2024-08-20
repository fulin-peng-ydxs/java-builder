package builder.core.build.builder.web.controller.mybatis.plus;


import builder.core.build.builder.web.controller.basic.ControllerBuildExecutor;

/**
 * mybatis-plus控制器构建执行器
 * author: pengshuaifeng
 * 2023/9/24
 */
public class MybatisPlusControllerBuildExecutor extends ControllerBuildExecutor {

    public static final MybatisPlusControllerBuildExecutor INSTANCE = new MybatisPlusControllerBuildExecutor();

    public MybatisPlusControllerBuildExecutor(){
        this("/template/web/controller/mybatis/plus/MybatisPlusControllerTemplate.txt");
    }
    public MybatisPlusControllerBuildExecutor(String templatePath){
        super(templatePath);
    }
}
