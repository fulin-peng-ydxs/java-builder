package builder.core.build.builder.mybatis;

import builder.core.build.resolve.database.DataBaseResolver;
import builder.core.build.response.FileResponder;
import builder.model.build.config.enums.ClassStructure;
import builder.model.build.config.type.MybatisType;
import builder.model.build.orm.Entity;
import builder.model.build.orm.Field;
import builder.model.build.orm.mybatis.Mapper;
import builder.model.build.orm.enums.FieldType;
import builder.model.resolve.database.ColumnInfo;
import builder.model.resolve.database.TableInfo;
import builder.model.resolve.database.jdbc.ConnectionInfo;
import builder.util.ClassUtil;
import builder.util.StringUtil;
import lombok.Getter;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * 抽象的mybatis构建器
 * author: pengshuaifeng
 * 2023/9/3
 */
@Getter
public abstract class MybatisBuilder {

    //构建实体集合
    protected  List<Entity> entities=new LinkedList<>();
    //构建映射集合
    protected  List<Mapper> mappers=new LinkedList<>();
    //文件响应器
    protected  FileResponder responder=new FileResponder();
    //实体构建路径
    protected String entityPath;
    //映射构建路径
    protected String mapperPath;
    //映射文件构建路径
    protected String mapperXmlPath;

    /**
     * 构造器
     * 2023/9/3 15:37
     * @author pengshuaifeng
     */
    public MybatisBuilder(ConnectionInfo connectionInfo, String rootPath,
                          String entityPath, String mapperPath, String mapperXmlPath){
        this(connectionInfo,entityPath,mapperPath,mapperXmlPath);
        if(StringUtil.isNotEmpty(rootPath))
            this.responder.setRootPath(rootPath);
    }

    public MybatisBuilder(ConnectionInfo connectionInfo, String entityPath, String mapperPath, String mapperXmlPath){
        this(entityPath,mapperPath,mapperXmlPath);
        DataBaseResolver dataBaseResolver = new DataBaseResolver(connectionInfo);
        List<TableInfo> tableInfos = dataBaseResolver.getTableInfos();
        for (TableInfo tableInfo : tableInfos) {
            Entity entity = convertEntity(tableInfo,entityPath);
            entities.add(entity);
            Mapper mapper = convertMapper(entity, mapperPath);
            mappers.add(mapper);
        }
    }

    public MybatisBuilder(String entityPath, String mapperPath, String mapperXmlPath){
        this.entityPath=entityPath;
        this.mapperPath=mapperPath;
        this.mapperXmlPath=mapperXmlPath;
    }

    public MybatisBuilder(ConnectionInfo connectionInfo, String rootPath){
        this(connectionInfo,rootPath,"java"+ File.separator+"entity","java"+File.separator+"mapper",
                "resource"+File.separator+"mapper");
    }

    /**
     * table转entity对象
     * 2023/9/3 20:04
     * @author pengshuaifeng
     */
    protected Entity convertEntity(TableInfo tableInfo,String path){
        Entity entity = new Entity();
        entity.setName(ClassUtil.generateStructureName(tableInfo.getName(),"_", ClassStructure.NAME));
        entity.setReference(ClassUtil.generateReferencePath(path)+"."+entity.getName());
        LinkedList<Field> fields = new LinkedList<>();
        for (ColumnInfo columnInfo : tableInfo.getColumnInfos()) {
            Field field = new Field();
            Class<?> javaType = FieldType.supportType(columnInfo.getType()).javaType;
            field.setReference(javaType.getName());
            field.setType(javaType);
            field.setName(ClassUtil.generateStructureName(columnInfo.getName(),"_", ClassStructure.ATTRIBUTES));
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

    /**
     * table转mapper对象
     * 2023/9/3 20:05
     * @author pengshuaifeng
     */
    protected Mapper convertMapper(Entity entity,String path){
        Mapper mapper = new Mapper();
        mapper.setName(entity.getName()+"Mapper");
        mapper.setEntity(entity);
        mapper.setReference(ClassUtil.generateReferencePath(path)+"."+mapper.getName());
        mapper.setDescription(entity.getTableInfo().getDescription()+"映射器");
        return mapper;
    }



    /**
     * 构建方法
     * 2023/9/3 15:17
     * @author pengshuaifeng
     * @param mybatisType 根据构建类型生成需要的组件
     */
    public void build(MybatisType mybatisType){
        if(mybatisType == MybatisType.ALL){
            for (Entity entity : entities) {
                buildEntity(entity);
            }
            for (Mapper mapper : mappers) {
                buildMapper(mapper);
                buildMapperXml(mapper);
            }
        }
        if (mybatisType == MybatisType.ENTITY) {
            for (Entity entity : entities) {
                buildEntity(entity);
            }
        }else{
            for (Mapper mapper : mappers) {
                if(mybatisType == MybatisType.MAPPER_XML){
                    buildMapper(mapper);
                    buildMapperXml(mapper);
                }else if(mybatisType == MybatisType.XML){
                    buildMapperXml(mapper);
                }else if(mybatisType == MybatisType.MAPPER){
                    buildMapper(mapper);
                }
            }
        }
    }

    /**
     * 构建实体
     * 2023/9/3 15:20
     * @author pengshuaifeng
     */
    protected void buildEntity(Entity entity){
        buildEntity(entity,this.entityPath);
    };

    /**
     * 构建实体
     * 2023/9/5 21:40
     * @author pengshuaifeng
     * @param path 实体构建输出路径
     */
    protected abstract void buildEntity(Entity entity,String path);

    /**
     * 构建映射器
     * 2023/9/3 15:20
     * @author pengshuaifeng
     */
    protected void buildMapper(Mapper mapper){
        buildMapper(mapper,this.mapperPath);
    }

    /**
     * 构建映射器
     * 2023/9/5 21:41
     * @author pengshuaifeng
     * @param path 映射器构建输出路径
     */
    protected abstract void buildMapper(Mapper mapper,String path);

    /**
     * 构建映射文件
     * 2023/9/3 15:20
     * @author pengshuaifeng
     */
    protected void buildMapperXml(Mapper mapper){
        buildMapperXml(mapper,this.mapperXmlPath);
    }

    /**
     * 构建映射器文件
     * 2023/9/5 21:42
     * @author pengshuaifeng
     * @param path 构建映射器文件输出路径
     */
    protected abstract void buildMapperXml(Mapper mapper,String path);
}
