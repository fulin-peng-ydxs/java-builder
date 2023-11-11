package builder.core.build.builder.web.controller.basic;


/**
 * JSR303控制器构建执行器
 * author: pengshuaifeng
 * 2023/11/11
 */
public class JSR303ControllerBuildExecutor extends ControllerBuildExecutor{

    public JSR303ControllerBuildExecutor(){
        this("/template/web/controller/JSR303ControllerTemplate.txt");
    }

    public JSR303ControllerBuildExecutor(String templatePath){
        super(templatePath);
    }

}
