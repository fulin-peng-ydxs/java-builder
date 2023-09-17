package builder.model.build.config.template.path;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * mybatis构建模版路径
 * author: pengshuaifeng
 * 2023/9/4
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MybatisTemplatePath {
    private String entityPath;
    private String mapperPath;
    private String mapperXmlPath;
}
