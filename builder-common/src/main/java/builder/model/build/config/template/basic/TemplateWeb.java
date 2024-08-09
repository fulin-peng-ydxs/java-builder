package builder.model.build.config.template.basic;


import lombok.Data;

/**
 * entity模版
 *
 * @author pengshuaifeng
 * 2024/1/28
 */
@Data
public class TemplateWeb {

    public static final TemplateWeb defaultInstance=new TemplateWeb();

    private boolean serviceEnable =true;

    private boolean controllerEnable =true;
}
