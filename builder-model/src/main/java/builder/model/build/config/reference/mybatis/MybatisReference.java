package builder.model.build.config.reference.mybatis;


import lombok.Data;

/**
 * mybatis构建引用路径
 * author: pengshuaifeng
 * 2023/9/3
 */
@Data
public class MybatisReference {
    //实体引用
    private String entity;
    //映射引用
    private String mapper;
}
