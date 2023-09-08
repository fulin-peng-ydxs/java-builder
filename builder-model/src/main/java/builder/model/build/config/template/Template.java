package builder.model.build.config.template;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;
import java.util.Map;

/**
 * 模版对象
 * author: pengshuaifeng
 * 2023/9/5
 */
@Data
@AllArgsConstructor
public class Template {
    private String template;
    private List<String> templates;
    private Map<String,String> templateClones;
}
