package builder.model.build.config.template.basic;


import lombok.Data;

import java.util.Collections;
import java.util.List;

/**
 * entity模版
 *
 * @author pengshuaifeng
 * 2024/1/28
 */
@Data
public class TemplateEntity {

    public static final TemplateEntity defaultInstance=new TemplateEntity();

    private boolean buildEnable=true;

    private boolean jsr303Enable=true;

    private boolean swaggerEnable=true;

    private boolean dictEnable=false;

    private List<String> ignoreNotNullMarkFields= Collections.emptyList();

}
