package builder.core.build.builder.entity.base;

import builder.model.build.config.BuildGlobalConfig;
import builder.model.build.orm.entity.Field;
import lombok.extern.slf4j.Slf4j;
import java.util.Set;

/**
 * 字典-实体构建器
 * author: pengshuaifeng
 * 2023/11/9
 */
@Slf4j
public class DictEntityBuilder extends AnnotationEntityBuilder {

    public DictEntityBuilder(){
        super();
    }

    @Override
    protected void fieldAddExecute(String annotationsValue,StringBuilder annotationBuilder,String annotationTemplateClone ,Field field, Set<String> cloneImports){
        if (!BuildGlobalConfig.templateEntity.isDictEnable()) {
            log.debug("dict-实体构建器不参与构建：isDictEnable={}",false);
            return;
        }
        //TODO 暂未实现
    }
}
