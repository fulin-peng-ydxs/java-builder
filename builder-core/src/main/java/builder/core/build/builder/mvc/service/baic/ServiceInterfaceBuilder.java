package builder.core.build.builder.mvc.service.baic;


import builder.core.build.builder.base.BaseBuilder;
import builder.core.build.builder.mvc.service.ServiceBuilder;
import builder.model.build.mvc.service.Service;
import builder.model.build.orm.Entity;
import builder.model.build.orm.Field;
import builder.util.StringUtil;
import builder.util.TemplateUtil;

import java.util.Map;
/**
 * 服务接口构建器
 * author: pengshuaifeng
 * 2023/9/23
 */
public class ServiceInterfaceBuilder extends BaseBuilder {

    public ServiceInterfaceBuilder(){
        this("/template/mvc/service/ServiceInterfaceTemplate.txt");
    }
    public ServiceInterfaceBuilder(String templatePath){
       super(templatePath);
    }

    /**
     * 构建服务接口
     * 2023/9/23 12:55
     * @author pengshuaifeng
     */
    public String buildInterface(Service service){
        Map<String, String> basicPadding = ServiceBuilder.buildBasicPadding(service, template);
        Entity entity = service.getEntity();
        Field primaryField = entity.getPrimaryField();
        basicPadding.put("{PrimaryKeyField}",primaryField.getType().getSimpleName());
        basicPadding.put("{primaryKeyField}", primaryField.getName());
        //克隆模版填充
        String cloneImportsTemplate = template.getTemplateClones().get("cloneImports");
        basicPadding.put("{cloneImports}",//克隆模版内容构建
                StringUtil.clearLastSpan(TemplateUtil.paddingTemplate(cloneImportsTemplate, "{import}", entity.getReference())));
        return TemplateUtil.paddingTemplate(template.getTemplate(),basicPadding);
    }
}
