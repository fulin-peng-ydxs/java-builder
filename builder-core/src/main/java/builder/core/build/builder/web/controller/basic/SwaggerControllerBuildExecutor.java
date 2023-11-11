package builder.core.build.builder.web.controller.basic;


/**
 * swagger控制器构建执行器
 * author: pengshuaifeng
 * 2023/11/11
 */
public class SwaggerControllerBuildExecutor extends ControllerBuildExecutor{

    public SwaggerControllerBuildExecutor(){
        this("/template/web/controller/SwaggerControllerTemplate.txt");
    }

    public SwaggerControllerBuildExecutor(String templatePath){
        super(templatePath);
    }

}
