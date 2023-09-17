package builder.core.build.builder.mvc.service.mybatis;


import builder.core.build.builder.mvc.service.ServiceBuilder;
import builder.model.build.config.template.Template;
import builder.model.build.mvc.service.ServiceImpl;
import builder.model.build.mvc.service.ServiceInterface;

/**mybatis服务构建器
 * author: pengshuaifeng
 * 2023/9/12
 */
public class MybatisServiceBuilder extends ServiceBuilder {

    //mybatis构建器
//    protected SimpleMybatisBuilder mybatisBuilder;


    @Override
    public String buildImpl(ServiceImpl serviceImpl, ServiceInterface serviceInterface,Template serviceTemplate) {
        //TODO 待后续实现
//        //基础模版填充
//        Map<String, String> paddings = buildBasic(service, serviceTemplate);
//        Entity entity = service.getEntity();
//        Field primaryField = entity.getPrimaryField();
//        paddings.put("{PrimaryKeyField}",primaryField.getType().getSimpleName());
//        paddings.put("{primaryKeyField}", ClassUtil.generateStructureName(primaryField.getColumnInfo().getName(),"-",
//                ClassStructure.ATTRIBUTES));
//        //克隆模版填充
//        String cloneImportsTemplate = serviceTemplate.getTemplateClones().get("cloneImports");   //获取克隆模版
//        StringBuilder cloneImportsBuilder = new StringBuilder(); //克隆模版内容构建
//        cloneImportsBuilder.append(TemplateUtil.paddingTemplate(cloneImportsTemplate,"{import}",));
        return null;
    }
}
