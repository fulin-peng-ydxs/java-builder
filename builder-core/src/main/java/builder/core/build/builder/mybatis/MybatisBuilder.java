package builder.core.build.builder.mybatis;

import builder.core.build.builder.entity.EntityBuilder;
import builder.core.build.builder.mybatis.mapper.MapperBuilder;
import builder.core.build.resolve.database.DataBaseResolver;
import builder.core.build.response.FileResponder;
import builder.model.build.config.content.orm.MybatisContent;
import builder.model.build.config.enums.ClassStructure;
import builder.model.build.config.path.MybatisPath;
import builder.model.build.config.template.Template;
import builder.model.build.config.template.path.MybatisTemplatePath;
import builder.model.build.orm.Entity;
import builder.model.build.orm.Field;
import builder.model.build.orm.enums.FieldType;
import builder.model.build.orm.mybatis.Mapper;
import builder.model.resolve.database.ColumnInfo;
import builder.model.resolve.database.TableInfo;
import builder.model.resolve.database.jdbc.ConnectionInfo;
import builder.util.ClassUtil;
import builder.util.StringUtil;
import builder.util.TemplateUtil;
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
    protected  List<Entity> entities;
    protected  List<Mapper> mappers;
    //构建源-数据库
    protected  ConnectionInfo connectionInfo;
    //构建路径
    protected String rootPath;
    protected String entityPath;
    protected String mapperPath;
    protected String mapperXmlPath;
    protected MybatisPath mybatisPath;
    //构建模版
    protected Template entityTemplate;
    protected Template mapperTemplate;
    protected Template mapperXmlTemplate;
    protected MybatisTemplatePath mybatisTemplatePath;
    //通用构建器
    protected EntityBuilder entityBuilder;
    protected MapperBuilder mapperBuilder;
    //构建响应器
    protected  FileResponder responder;

    /**
     * 构建器创建
     * 2023/9/17 19:18
     * @author pengshuaifeng
     */
    MybatisBuilder(List<Entity> entities, List<Mapper> mappers, ConnectionInfo connectionInfo, String rootPath, String entityPath, String mapperPath, String mapperXmlPath, MybatisPath mybatisPath, Template entityTemplate, Template mapperTemplate, Template mapperXmlTemplate, MybatisTemplatePath mybatisTemplatePath, EntityBuilder entityBuilder, MapperBuilder mapperBuilder, FileResponder responder) {
        this.entities = entities;
        this.mappers = mappers;
        this.connectionInfo = connectionInfo;
        this.rootPath = rootPath;
        this.entityPath = entityPath;
        this.mapperPath = mapperPath;
        this.mapperXmlPath = mapperXmlPath;
        this.mybatisPath = mybatisPath;
        this.entityTemplate = entityTemplate;
        this.mapperTemplate = mapperTemplate;
        this.mapperXmlTemplate = mapperXmlTemplate;
        this.mybatisTemplatePath = mybatisTemplatePath;
        this.entityBuilder = entityBuilder;
        this.mapperBuilder = mapperBuilder;
        this.responder = responder;
        init();
    }

    /**
     * 构建器初始化
     * 2023/9/17 19:18
     * @author pengshuaifeng
     */
    protected void init(){
        //初始化构建数据
        initBuildData();
        //初始化路径
        initBuildPath();
        //初始化模版
        initTemplate();
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
        if(mybatisPath!=null){
            entityPath=mybatisPath.getEntity();
            mapperPath=mybatisPath.getMapper();
            mapperXmlPath=mybatisPath.getMapperXml();
            if(StringUtil.isEmpty(rootPath))
                rootPath=mybatisPath.getRoot();
        }
        entityPath=StringUtil.isNotEmpty(entityPath)?entityPath:"java"+ File.separator+"entity";
        mapperPath=StringUtil.isNotEmpty(mapperPath)?mapperPath:"java"+File.separator+"mapper";
        mapperXmlPath=StringUtil.isNotEmpty(mapperXmlPath)?mapperXmlPath:"resource"+File.separator+"mapper";
    }

    public void initTemplate(){
        initTemplatePath();
        String entityTemplatePath = mybatisTemplatePath.getEntityPath();
        String mapperTemplatePath = mybatisTemplatePath.getMapperPath();
        String mapperXmlTemplatePath = mybatisTemplatePath.getMapperXmlPath();
        this.entityTemplate=new Template(TemplateUtil.getTemplate(entityTemplatePath),
                TemplateUtil.getTemplates(entityTemplatePath),TemplateUtil.getCloneTemplates(TemplateUtil.getCloneTemplatePath(entityTemplatePath)));
        try {
            this.mapperTemplate=new Template(TemplateUtil.getTemplate(mapperTemplatePath),
                    TemplateUtil.getTemplates(mapperTemplatePath),TemplateUtil.getCloneTemplates(TemplateUtil.getCloneTemplatePath(mapperTemplatePath)));
        } catch (Exception e) {
            this.mapperTemplate=new Template(TemplateUtil.getTemplate(mapperTemplatePath),
                    TemplateUtil.getTemplates(mapperTemplatePath),null);
        }
        this.mapperXmlTemplate= new Template(TemplateUtil.getTemplate(mapperXmlTemplatePath),
                TemplateUtil.getTemplates(mapperXmlTemplatePath),TemplateUtil.getCloneTemplates(TemplateUtil.getCloneTemplatePath(mapperXmlTemplatePath)));
    }

    protected void initTemplatePath(){
        mybatisTemplatePath=mybatisTemplatePath==null?
                new MybatisTemplatePath():mybatisTemplatePath;
       if(StringUtil.isEmpty(mybatisTemplatePath.getEntityPath())){
            mybatisTemplatePath.setEntityPath("/template/basic/EntityTemplate.txt");
        }
        if (StringUtil.isEmpty(mybatisTemplatePath.getMapperPath())) {
            mybatisTemplatePath.setMapperPath("/template/mybatis/simple/MapperTemplate.txt");
        }
        if (StringUtil.isEmpty(mybatisTemplatePath.getMapperXmlPath())) {
            mybatisTemplatePath.setMapperXmlPath("/template/mybatis/simple/MapperXmlTemplate.txt");
        }
    }

    public void initBuilder(){
        entityBuilder=entityBuilder==null?new EntityBuilder():entityBuilder;
        mapperBuilder=mapperBuilder==null?new MapperBuilder():mapperBuilder;
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
    protected void setBuildData(DataBaseResolver dataBaseResolver){
        List<TableInfo> tableInfos = dataBaseResolver.getTableInfos();
        setBuildData(tableInfos);
    }

    protected void setBuildData(List<TableInfo> tableInfos){
        for (TableInfo tableInfo : tableInfos) {
            Entity entity = convertEntity(tableInfo,entityPath);
            entities.add(entity);
            Mapper mapper = convertMapper(entity, mapperPath);
            mappers.add(mapper);
        }
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
        mapper.setDescription(entity.getName()+"映射器");
        return mapper;
    }


    /**
     * 构建执行
     * 2023/9/3 15:17
     * @author pengshuaifeng
     * @param mybatisContent 根据构建类型生成需要的组件
     */
    protected void buildExecute(MybatisContent mybatisContent){
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
    protected void buildEntity(Entity entity, String path) {
        String entityValue = buildEntityValue(entity);
        responder.execute(entityValue,entity.getName()+".java",path);
    }

    protected String buildEntityValue(Entity entity){
        return entityBuilder.build(entity,entityTemplate);
    }

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
    protected void buildMapper(Mapper mapper, String path) {
        String mapperValue = buildMapperValue(mapper);
        responder.execute(mapperValue,mapper.getName()+".java",path);
    }

    protected String buildMapperValue(Mapper mapper){
        return mapperBuilder.buildMapper(mapper,mapperTemplate);
    }

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
    protected void buildMapperXml(Mapper mapper, String path) {
        String mapperXmlValue = buildMapperXmlValue(mapper);
        responder.execute(mapperXmlValue,mapper.getName()+".xml",path);
    }

    protected String buildMapperXmlValue(Mapper mapper){
        return mapperBuilder.buildMapperXml(mapper,mapperXmlTemplate);
    }
}
