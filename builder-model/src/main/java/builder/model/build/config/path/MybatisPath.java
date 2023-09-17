package builder.model.build.config.path;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * mybatis构建路径
 * author: pengshuaifeng
 * 2023/9/3
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MybatisPath extends BasePath {
    //实体路径
    private String entity;
    //映射路径
    private String mapper;
    //映射器文件路径
    private String mapperXml;
}