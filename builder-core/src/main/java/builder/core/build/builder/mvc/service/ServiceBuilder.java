package builder.core.build.builder.mvc.service;

import builder.model.build.config.template.Template;
import builder.model.build.mvc.service.Service;
import builder.model.build.mvc.service.ServiceImpl;
import builder.model.build.mvc.service.ServiceInterface;
import builder.model.build.orm.Entity;
import builder.util.TemplateUtil;
import java.util.HashMap;
import java.util.Map;

/**
 * 服务构建器
 * author: pengshuaifeng
 * 2023/9/12
 */
public abstract class ServiceBuilder {


    /**
     * 构建
     * 2023/9/18 00:26
     * @author pengshuaifeng
     */


    /**
     * 构建服务实现
     * 2023/9/11 22:41
     * @author pengshuaifeng
     */
    public abstract String buildImpl(ServiceImpl serviceImpl,ServiceInterface serviceInterface,Template serviceTemplate);

    /**
     * 构建服务接口
     * 2023/9/12 23:09
     * @author pengshuaifeng
     */
    public String buildInterface(ServiceInterface serviceInterface, Template serviceTemplate){
        return TemplateUtil.paddingTemplate(serviceTemplate.getTemplate(),buildBasic(serviceInterface, serviceTemplate));
    }

    /**
     * 构建基础服务定义
     * 2023/9/12 23:10
     * @author pengshuaifeng
     */
    protected Map<String,String> buildBasic(Service service ,Template serviceTemplate){
        Map<String, String> paddings = new HashMap<>();
        //基础模版填充
        paddings.put("{package}", service.getReference());
        paddings.put("{description}", service.getDescription());
        paddings.put("{serviceName}", service.getName());
        Entity entity = service.getEntity();
        paddings.put("{Entity}", entity.getName());
        //克隆模版填充
        String cloneImportsTemplate = serviceTemplate.getTemplateClones().get("cloneImports");   //获取克隆模版
        paddings.put("{cloneImports}",//克隆模版内容构建
                TemplateUtil.paddingTemplate(cloneImportsTemplate, "{import}", entity.getReference()));
        return paddings;
    }

}
