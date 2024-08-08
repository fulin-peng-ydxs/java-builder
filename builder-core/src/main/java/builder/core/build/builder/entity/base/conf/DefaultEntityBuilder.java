package builder.core.build.builder.entity.base.conf;

import builder.core.build.builder.entity.base.DictEntityBuilder;
import builder.core.build.builder.entity.base.JSR303EntityBuilder;
import builder.core.build.builder.entity.base.SwaggerEntityBuilder;

public abstract class DefaultEntityBuilder {

    public static final SwaggerEntityBuilder swagger =new SwaggerEntityBuilder();

    public static final JSR303EntityBuilder jsr303 =new JSR303EntityBuilder();

    public static final DictEntityBuilder dict =new DictEntityBuilder();

}
