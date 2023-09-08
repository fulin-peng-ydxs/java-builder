package builder.core.build.builder.mybatis.mapper;


import builder.model.build.config.enums.ClassStructure;
import builder.model.build.config.template.Template;
import builder.model.build.orm.Entity;
import builder.model.build.orm.Field;
import builder.model.build.orm.mybatis.Mapper;
import builder.util.ClassUtil;
import builder.util.TemplateUtil;
import java.util.HashMap;
import java.util.Map;

/**
 * mapper构建器
 * author: pengshuaifeng
 * 2023/9/9
 */
public class MapperBuilder {

    /**
     * 构建实体
     * 2023/9/6 21:53
     * @author pengshuaifeng
     */
    public static String build(Mapper mapper,Template mapperTemplate){
        Map<String, String> paddings = new HashMap<>();
        //基础模版填充
        Entity entity = mapper.getEntity();
        paddings.put("{package}",mapper.getReference());
        paddings.put("{import}",entity.getReference());
        paddings.put("{mapperName}",mapper.getName());
        paddings.put("{description}",mapper.getDescription());
        paddings.put("{Entity}",entity.getName());
        paddings.put("{entity}", ClassUtil.generateStructureName(entity.getTableInfo().getName(),"-",
                ClassStructure.ATTRIBUTES));
        Field primaryField = entity.getPrimaryField();
        paddings.put("{PrimaryKeyField}",primaryField.getType().getSimpleName());
        paddings.put("{primaryKeyField}", ClassUtil.generateStructureName(primaryField.getColumnInfo().getName(),"-",
                ClassStructure.ATTRIBUTES));
        return TemplateUtil.paddingTemplate(mapperTemplate.getTemplate(),paddings);
    }

}
