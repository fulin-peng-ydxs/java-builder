package builder.core.build.builder.entity.base;


import builder.core.build.builder.entity.EntityBuilder;
import builder.model.build.orm.Field;
import builder.util.TemplateUtil;

import java.util.Map;

/**
 * JSR303规范-实体构建器
 * author: pengshuaifeng
 * 2023/11/9
 */
public class JSR303EntityBuilder extends EntityBuilder {

    public JSR303EntityBuilder(){
        this("/template/basic/JSR303EntityTemplate.txt");
    }

    public JSR303EntityBuilder(String templatePath){
        super(templatePath);
    }

    @Override
    protected void fieldAdd(Map<String, String> cloneFieldPaddings, Field field,StringBuilder cloneImportsBuilder) {
        if(!field.getColumnInfo().isNull()){
            cloneFieldPaddings.put("{JSR303}","@NotNull(message=\""+field.getColumnInfo().getDescription()+"不能为空\")");
            String cloneImports = template.getTemplateClones().get("cloneImports");
            cloneImportsBuilder.append(TemplateUtil.paddingTemplate(cloneImports,"{import}","javax.validation.constraints.NotNull"));
        }else{
            cloneFieldPaddings.put("{JSR303}","!empty!");
        }
    }
}
