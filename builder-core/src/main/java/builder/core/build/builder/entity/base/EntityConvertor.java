package builder.core.build.builder.entity.base;


import builder.model.build.config.BuildGlobalConfig;
import builder.model.build.config.enums.ClassStructure;
import builder.model.build.orm.Entity;
import builder.model.build.orm.Field;
import builder.model.build.orm.enums.FieldType;
import builder.model.resolve.database.ColumnInfo;
import builder.model.resolve.database.TableInfo;
import builder.util.ClassUtils;

import java.util.LinkedList;

/**
 * entity对象转换器
 * author: pengshuaifeng
 * 2023/11/9
 */
public class EntityConvertor {

    /**
     * tableInfo转entity
     * 2023/11/9 21:57
     * @author pengshuaifeng
     */
    public static Entity convertEntity(TableInfo tableInfo, String path){
        Entity entity = new Entity();
        entity.setName(BuildGlobalConfig.templatePrefix +ClassUtils.generateStructureName(tableInfo.getName(),
                        "_", ClassStructure.NAME)+BuildGlobalConfig.templatePrefix);
        String referencePath = ClassUtils.generateReferencePath(path);
        entity.setReference(referencePath +"."+entity.getName());
        entity.setPackages(referencePath);
        LinkedList<Field> fields = new LinkedList<>();
        for (ColumnInfo columnInfo : tableInfo.getColumnInfos()) {
            Field field = new Field();
            Class<?> javaType = FieldType.supportType(columnInfo.getType()).javaType;
            field.setReference(javaType.getName());
            field.setType(javaType);
            field.setName(ClassUtils.generateStructureName(columnInfo.getName(),"_", ClassStructure.ATTRIBUTES));
            field.setColumnInfo(columnInfo);
            fields.add(field);
            if(columnInfo.isPrimaryKey()){
                entity.setPrimaryField(field);
            }
        }
        entity.setTableInfo(tableInfo);
        entity.setFields(fields);
        return entity;
    }

}
