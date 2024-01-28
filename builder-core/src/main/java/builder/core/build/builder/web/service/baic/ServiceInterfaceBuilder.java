package builder.core.build.builder.web.service.baic;


import builder.core.build.builder.base.Builder;
import builder.core.build.builder.web.service.ServiceBuilderProcessor;
import builder.model.build.orm.Entity;
import builder.model.build.orm.Field;
import builder.model.build.web.service.Service;
import builder.util.TemplateUtils;

import java.util.Map;
/**
 * 服务接口构建器
 * author: pengshuaifeng
 * 2023/9/23
 */
public class ServiceInterfaceBuilder extends Builder {

    public ServiceInterfaceBuilder(){
        this("/template/web/service/ServiceInterfaceTemplate.txt");
    }
    public ServiceInterfaceBuilder(String templatePath){
       this(templatePath,"/template/web/service/ServiceInterfaceTemplate.txt");
    }

    public ServiceInterfaceBuilder(String templatePath,String templateClonePath){
        super(templatePath,templateClonePath);
    }

    /**
     * 构建服务接口
     * 2023/9/23 12:55
     * @author pengshuaifeng
     */
    public String buildInterface(Service service){
        Map<String, String> basicPadding = ServiceBuilderProcessor.buildBasicPadding(service, template);
        Entity entity = service.getEntity();
        Field primaryField = entity.getPrimaryField();
        basicPadding.put("{PrimaryKeyField}",primaryField.getType().getSimpleName());
        basicPadding.put("{primaryKeyField}", primaryField.getName());
        //克隆模版填充
        String cloneImportsTemplate = template.getTemplateClones().get("cloneImports");
        basicPadding.put("{cloneImports}",//克隆模版内容构建
                TemplateUtils.paddingTemplate(cloneImportsTemplate, "{import}", entity.getReference()));
        return TemplateUtils.paddingTemplate(template.getTemplate(),basicPadding);
    }
}
