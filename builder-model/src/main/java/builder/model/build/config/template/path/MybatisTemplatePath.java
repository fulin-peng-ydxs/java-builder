package builder.model.build.config.template.path;


import lombok.Data;

/**
 * mybatis构建模版路径
 * author: pengshuaifeng
 * 2023/9/4
 */
@Data
public class MybatisTemplatePath {
    private String entityPath;
    private String mapperPath;
    private String mapperXmlPath;
}
