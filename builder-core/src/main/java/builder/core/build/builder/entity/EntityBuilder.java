package builder.core.build.builder.entity;

import builder.model.build.config.template.Template;
import builder.model.build.orm.Entity;
import builder.model.build.orm.Field;
import builder.util.ClassUtil;
import builder.util.StringUtil;
import builder.util.TemplateUtil;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 实体构建器
 * author: pengshuaifeng
 * 2023/9/6
 */
@Data
public class EntityBuilder {

    /**
     * 构建实体
     * 2023/9/6 21:53
     * @author pengshuaifeng
     */
    public  String build(Entity entity, Template entityTemplate){
        Map<String, String> paddings = new HashMap<>();
        //基础模版填充
        paddings.put("{package}",entity.getReference());
        paddings.put("{entityName}",entity.getName());
        paddings.put("{description}",entity.getTableInfo().getDescription());
        //克隆模版填充
        Map<String, String> templateClones = entityTemplate.getTemplateClones();
        String cloneFieldsTemplate = templateClones.get("cloneFields");
        String cloneImportsTemplate = templateClones.get("cloneImports");   //获取克隆模版
        Map<String, String> cloneFieldPaddings = new HashMap<>();
        Map<String, String> cloneImportPaddings = new HashMap<>();  //克隆模版内容填充
        StringBuilder cloneFieldsBuilder = new StringBuilder();
        StringBuilder cloneImportsBuilder = new StringBuilder(); //克隆模版内容构建
        List<Field> fields = entity.getFields();
        for (int i = 0; i < fields.size(); i++) {
            Field field = fields.get(i);
            cloneFieldPaddings.put("{fieldType}", field.getType().getSimpleName());
            cloneFieldPaddings.put("{filedName}", field.getName());
            cloneFieldPaddings.put("{comment}", field.getColumnInfo().getDescription());
            String reference = field.getReference();
            if(!ClassUtil.ignoreReference(reference)){ //引用是否需要导入
                cloneImportPaddings.put("{import}", reference);
                cloneImportsBuilder.append(TemplateUtil.paddingTemplate(cloneImportsTemplate, cloneImportPaddings));
            }
            cloneFieldsBuilder.append(TemplateUtil.paddingTemplate(cloneFieldsTemplate,cloneFieldPaddings));
        }
        paddings.put("{cloneFields}", StringUtil.clearLastSpan(cloneFieldsBuilder.toString()));
        paddings.put("{cloneImports}",cloneImportsBuilder.length()==0?"":cloneImportsBuilder.toString());
        return TemplateUtil.paddingTemplate(entityTemplate.getTemplate(),paddings);
    }

}
