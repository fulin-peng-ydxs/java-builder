package builder.core.build.builder.entity.base;


import builder.model.build.config.BuildGlobalConfig;
import builder.model.build.orm.entity.Field;
import builder.model.resolve.database.ColumnInfo;
import builder.util.TemplateUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

/**
 * JSR303规范-实体构建器
 * author: pengshuaifeng
 * 2023/11/9
 */
@Slf4j
public class JSR303EntityBuilder extends AnnotationEntityBuilder {

    //TODO 非模版核心构建器，可不用创建template对象：通过构造参数或色方法传参，需要调整父构造函数
    public JSR303EntityBuilder(){
        super();
    }

    @Override
    protected void fieldAddExecute(StringBuilder annotationBuilder,String annotationTemplateClone ,Field field, Set<String> cloneImports){
        if (!BuildGlobalConfig.templateEntity.isJsr303Enable()) {
            log.debug("jsr_303-实体构建器不参与构建：isJsr303Enable={}",false);
            return;
        }
        ColumnInfo columnInfo = field.getColumnInfo();
        if(!columnInfo.isNull() && !columnInfo.isPrimaryKey()){
            annotationBuilder.append(annotationTemplateClone.replace("{Annotation}",
                    "@NotNull(message=\""+ columnInfo.getDescription()+"不能为空\")"));
            String cloneImportsTemplate = template.getTemplateClones().get("cloneImports");
            if (!cloneImports.contains("javax.validation.constraints.NotNull")) {
                cloneImports.add(TemplateUtils.paddingTemplate(cloneImportsTemplate,"{import}","javax.validation.constraints.NotNull"));
            }
        }
    }
}
