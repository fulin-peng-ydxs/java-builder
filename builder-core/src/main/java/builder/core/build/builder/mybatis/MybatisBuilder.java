package builder.core.build.builder.mybatis;

import builder.core.build.builder.entity.EntityBuilder;
import builder.core.build.builder.mybatis.mapper.MapperBuilder;
import builder.core.build.resolve.database.DataBaseResolver;
import builder.core.build.response.FileResponder;
import builder.core.build.response.Responder;
import builder.model.build.config.content.MybatisContent;
import builder.model.build.config.enums.ClassStructure;
import builder.model.build.orm.Entity;
import builder.model.build.orm.Field;
import builder.model.build.orm.enums.FieldType;
import builder.model.build.orm.mybatis.Mapper;
import builder.model.resolve.database.ColumnInfo;
import builder.model.resolve.database.TableInfo;
import builder.model.resolve.database.jdbc.ConnectionInfo;
import builder.util.ClassUtil;
import builder.util.StringUtil;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
/**
 * mybatis构建器
 * author: pengshuaifeng
 * 2023/9/3
 */
@Setter
@Getter
@Builder
public class MybatisBuilder {

    //构建数据
    private   List<Entity> entities;
    private  List<Mapper> mappers;
    //构建源-数据库
    private  ConnectionInfo connectionInfo;
    //构建路径
    private String rootPath;
    private String entityPath;
    private String mapperPath;
    private String mapperXmlPath;
    //构建器
    private EntityBuilder entityBuilder;
    private MapperBuilder mapperBuilder;
    //构建内容
    private MybatisContent mybatisContent;
    //构建响应器
    private Responder responder;

    /**
     * 构建器创建
     * 2023/9/17 19:18
     * @author pengshuaifeng
     */
    MybatisBuilder(List<Entity> entities, List<Mapper> mappers, ConnectionInfo connectionInfo, String rootPath, String entityPath, String mapperPath, String mapperXmlPath, EntityBuilder entityBuilder, MapperBuilder mapperBuilder,MybatisContent mybatisContent,Responder responder) {
        this.entities = entities;
        this.mappers = mappers;
        this.connectionInfo = connectionInfo;
        this.rootPath = rootPath;
        this.entityPath = entityPath;
        this.mapperPath = mapperPath;
        this.mapperXmlPath = mapperXmlPath;
        this.entityBuilder = entityBuilder;
        this.mapperBuilder = mapperBuilder;
        this.mybatisContent=mybatisContent;
        this.responder = responder;
        init();
    }

    /**
     * 构建器初始化
     * 2023/9/17 19:18
     * @author pengshuaifeng
     */
    private void init(){
        //初始化构建数据
        initBuildData();
        //初始化路径
        initBuildPath();
        //初始化构建器
        initBuilder();
        //初始化响应器
        initBuildResponder();
    }

    public void initBuildData(){
         entities=new LinkedList<>();
         mappers=new LinkedList<>();
    }

    public void initBuildPath(){
        entityPath=StringUtil.isNotEmpty(entityPath)?entityPath:"java"+ File.separator+"entity";
        mapperPath=StringUtil.isNotEmpty(mapperPath)?mapperPath:"java"+File.separator+"mapper";
        mapperXmlPath=StringUtil.isNotEmpty(mapperXmlPath)?mapperXmlPath:"resource"+File.separator+"mapper";
    }

    public void initBuilder(){
        entityBuilder=entityBuilder==null?new EntityBuilder():entityBuilder;
        mapperBuilder=mapperBuilder==null?new MapperBuilder():mapperBuilder;
        mybatisContent=mybatisContent==null?MybatisContent.ALL:mybatisContent;
    }

    public void initBuildResponder(){
        if(responder==null){
            responder=new FileResponder();
        }
        if(StringUtil.isNotEmpty(rootPath))
            responder.setRootPath(rootPath);
    }

    /**
     * 构建
     * 2023/9/17 20:11
     * @author pengshuaifeng
     */
    public void build(){
        build(mybatisContent);
    }

    public void build(MybatisContent mybatisContent){
        if(connectionInfo!=null){
            setBuildData(new DataBaseResolver(connectionInfo));
        }else throw new RuntimeException("没有构建源");
        buildExecute(mybatisContent);
    }

    /**
     * 生成构建数据
     * 2023/9/17 20:17
     * @author pengshuaifeng
     */
    private void setBuildData(DataBaseResolver dataBaseResolver){
        List<TableInfo> tableInfos = dataBaseResolver.getTableInfos();
        setBuildData(tableInfos);
    }

    private void setBuildData(List<TableInfo> tableInfos){
        for (TableInfo tableInfo : tableInfos) {
            Entity entity = convertEntity(tableInfo,entityPath);
            entities.add(entity);
            Mapper mapper = convertMapper(entity, mapperPath);
            mappers.add(mapper);
        }
    }

    /**
     * 构建执行
     * 2023/9/3 15:17
     * @author pengshuaifeng
     * @param mybatisContent 根据构建类型生成需要的组件
     */
    private void buildExecute(MybatisContent mybatisContent){
        if(mybatisContent == MybatisContent.ALL){
            for (Entity entity : entities) {
                buildEntity(entity);
            }
            for (Mapper mapper : mappers) {
                buildMapper(mapper);
                buildMapperXml(mapper);
            }
        } else if (mybatisContent == MybatisContent.ENTITY) {
            for (Entity entity : entities) {
                buildEntity(entity);
            }
        }else throw new RuntimeException("不支持的构建类型");
    }

    /**
     * 构建实体
     * 2023/9/5 21:40
     * @author pengshuaifeng
     */
    private void buildEntity(Entity entity) {
        String entityValue = buildEntityValue(entity);
        responder.execute(entityValue,entity.getName()+".java",this.entityPath);
    }

    private String buildEntityValue(Entity entity){
        return entityBuilder.build(entity);
    }

    /**
     * 构建映射器
     * 2023/9/5 21:41
     * @author pengshuaifeng
     */
    private void buildMapper(Mapper mapper) {
        String mapperValue = buildMapperValue(mapper);
        responder.execute(mapperValue,mapper.getName()+".java",this.mapperPath);
    }

    private String buildMapperValue(Mapper mapper){
        return mapperBuilder.buildMapper(mapper);
    }

    /**
     * 构建映射文件
     * 2023/9/3 15:20
     * @author pengshuaifeng
     */
    private void buildMapperXml(Mapper mapper){
        String mapperXmlValue = buildMapperXmlValue(mapper);
        responder.execute(mapperXmlValue,mapper.getName()+".xml",this.mapperXmlPath);
    }


    private String buildMapperXmlValue(Mapper mapper){
        return mapperBuilder.buildMapperXml(mapper);
    }

    /**
     * table转entity对象
     * 2023/9/3 20:04
     * @author pengshuaifeng
     */
    private Entity convertEntity(TableInfo tableInfo,String path){
        Entity entity = new Entity();
        entity.setName(ClassUtil.generateStructureName(tableInfo.getName(),"_", ClassStructure.NAME));
        String referencePath = ClassUtil.generateReferencePath(path);
        entity.setReference(referencePath +"."+entity.getName());
        entity.setPackages(referencePath);
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
    private Mapper convertMapper(Entity entity,String path){
        Mapper mapper = new Mapper();
        mapper.setName(entity.getName()+"Mapper");
        mapper.setEntity(entity);
        String referencePath = ClassUtil.generateReferencePath(path);
        mapper.setReference(referencePath +"."+mapper.getName());
        mapper.setPackages(referencePath);
        mapper.setDescription(entity.getName()+"映射器");
        return mapper;
    }

}
