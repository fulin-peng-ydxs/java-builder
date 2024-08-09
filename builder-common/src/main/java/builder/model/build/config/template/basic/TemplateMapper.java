package builder.model.build.config.template.basic;


import lombok.Data;

/**
 * mapper模版
 *
 * @author pengshuaifeng
 * 2024/1/28
 */
@Data
public class TemplateMapper {

    public static final TemplateMapper defaultInstance=new TemplateMapper();

    private boolean buildEnable=true;

    private boolean mapperEnable=true;

    private boolean mapperXmlEnable=true;
}
