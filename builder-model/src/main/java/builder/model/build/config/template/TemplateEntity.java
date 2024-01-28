package builder.model.build.config.template;


import lombok.Data;

/**
 * entity模版
 *
 * @author pengshuaifeng
 * 2024/1/28
 */
@Data
public class TemplateEntity {

    private boolean jsr303Enable=true;

    private boolean swaggerEnable=true;

}
