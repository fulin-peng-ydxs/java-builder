package builder.model.build.config.type;


/**
 * 构建mybatis类型
 * author: pengshuaifeng
 * 2023/9/3
 */
public enum MybatisType {
    //构建所有组件
    ALL,
    //仅构建实体
    ENTITY,
    //仅构建xml
    XML,
    //仅构建映射器
    MAPPER,
    //构建映射器和xml
    MAPPER_XML,
}
