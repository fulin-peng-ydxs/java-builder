package builder.core.build.builder.web.controller.basic;

import builder.core.build.builder.base.Builder;
import builder.model.build.orm.Entity;
import builder.model.build.orm.Field;
import builder.model.build.web.Controller;
import builder.model.build.web.service.Service;
import builder.util.ClassUtil;
import builder.util.StringUtil;
import builder.util.TemplateUtil;
import lombok.Getter;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 控制器构建执行器
 * author: pengshuaifeng
 * 2023/9/24
 */
@Getter
public class ControllerBuildExecutor extends Builder {

    private List<ControllerBuildExecutor> controllerBuildExecutors=new LinkedList<>();

    public ControllerBuildExecutor(){
        this("/template/web/controller/ControllerTemplate.txt");
    };

    public ControllerBuildExecutor(String templatePath){
        super(templatePath);
    }

    public ControllerBuildExecutor(String templatePath,List<ControllerBuildExecutor> controllerBuildExecutors){
        super(templatePath);
        this.controllerBuildExecutors=controllerBuildExecutors;
    }

    public ControllerBuildExecutor(List<ControllerBuildExecutor> controllerBuildExecutors){
        this.controllerBuildExecutors=controllerBuildExecutors;
    }

    /**
     * 构建控制器
     * 2023/9/23 12:55
     * @author pengshuaifeng
     */
    public String build(Controller controller){
        Map<String, String> paddings = buildBasicPadding(controller);
        Entity entity = controller.getEntity();
        Field primaryField = entity.getPrimaryField();
        paddings.put("{PrimaryKeyField}",primaryField.getType().getSimpleName());
        paddings.put("{primaryKeyField}", primaryField.getName());
        //克隆模版填充
        Service serviceInterface = controller.getServiceImpl().getServiceInterface();
        String cloneImportsTemplate = template.getTemplateClones().get("cloneImports");
        paddings.put("{cloneImports}",//克隆模版内容构建
                StringUtil.clearLastSpan(TemplateUtil.paddingTemplate(cloneImportsTemplate, "{import}", entity.getReference()))+
                StringUtil.clearLastSpan(TemplateUtil.paddingTemplate(cloneImportsTemplate, "{import}", serviceInterface.getReference()))
        );
        controllerBuildExecutors.forEach(value->{
            value.globalAdd(paddings);
        });
        return TemplateUtil.paddingTemplate(template.getTemplate(),paddings);
    }

    /**
     * 基础控制器模版填充
     * 2023/9/24 17:22
     * @author pengshuaifeng
     */
    protected Map<String,String> buildBasicPadding(Controller controller){
        Map<String, String> paddings = new HashMap<>();
        paddings.put("{package}", controller.getReference());
        paddings.put("{description}", controller.getDescription());
        Service serviceInterface = controller.getServiceImpl().getServiceInterface();
        paddings.put("{Service}", serviceInterface.getName());
        paddings.put("{service}", ClassUtil.nameToAttribute(serviceInterface.getName()));
        Entity entity = controller.getEntity();
        paddings.put("{Entity}", entity.getName());
        paddings.put("{entity}",ClassUtil.nameToAttribute(entity.getName()));
        paddings.put("{entityDescription}",controller.getEntity().getTableInfo().getDescription());
        return paddings;
    }

    /**
     * 全局补充扩展
     * 2023/9/19 23:26
     * @author pengshuaifeng
     */
    protected void globalAdd(Map<String, String> paddings){

    }
}
