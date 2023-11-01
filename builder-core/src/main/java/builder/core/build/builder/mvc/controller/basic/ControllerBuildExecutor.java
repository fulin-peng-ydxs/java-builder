package builder.core.build.builder.mvc.controller.basic;

import builder.core.build.builder.base.BaseBuilder;
import builder.core.build.builder.mvc.controller.ControllerBuilder;
import builder.model.build.mvc.Controller;
import builder.model.build.mvc.service.Service;
import builder.model.build.orm.Entity;
import builder.model.build.orm.Field;
import builder.util.StringUtil;
import builder.util.TemplateUtil;

import java.util.Map;

/**
 * 控制器构建执行器
 * author: pengshuaifeng
 * 2023/9/24
 */
public class ControllerBuildExecutor extends BaseBuilder {

    public ControllerBuildExecutor(){
        this("/template/mvc/controller/ControllerTemplate.txt");
    };

    public ControllerBuildExecutor(String templatePath){
        super(templatePath);
    }

    /**
     * 构建控制器
     * 2023/9/23 12:55
     * @author pengshuaifeng
     */
    public String build(Controller controller){
        Map<String, String> paddings = ControllerBuilder.buildBasicPadding(controller, template);
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
        return TemplateUtil.paddingTemplate(template.getTemplate(),buildExt(paddings));
    }

    /**
     * 构建扩展
     * 2023/9/24 19:14
     * @author pengshuaifeng
     */
    protected Map<String, String> buildExt(Map<String, String> paddings){
        return paddings;
    }
}
