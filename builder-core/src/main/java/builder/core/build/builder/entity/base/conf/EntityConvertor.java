package builder.core.build.builder.entity.base.conf;


import builder.model.build.config.BuildGlobalConfig;
import builder.model.build.config.enums.ClassStructure;
import builder.model.build.orm.entity.Entity;
import builder.model.build.orm.entity.Field;
import builder.model.build.orm.enums.FieldType;
import builder.model.resolve.database.ColumnInfo;
import builder.model.resolve.database.TableInfo;
import builder.util.ClassUtils;

import java.sql.Date;
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
            field.setClassStyleName(ClassUtils.generateStructureName(columnInfo.getName(),"_", ClassStructure.NAME));
            field.setColumnInfo(columnInfo);
            fields.add(field);
            if(columnInfo.isPrimaryKey()){
                entity.setPrimaryField(field);
            }
            if(field.getType()== Date.class){
                boolean onlyDate = FieldType.isOnlyDate(columnInfo.getType());
                field.setTimeType(onlyDate ? Field.TimeType.DATE : Field.TimeType.DATETIME);
            }
        }
        entity.setTableInfo(tableInfo);
        entity.setFields(fields);
        entity.setDescription(tableInfo.getDescription());
        return entity;
    }

}
