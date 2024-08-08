package builder.model.build.orm.annotation;

public @interface  Dict {

    /**
     * @return 字典描述
     */
    String message() default "";

    /**
     * @return 字典的Name
     */
    String dictName() ;

    /**
     * @return 字典的key
     */
    String dictKey();

    /**
     * @return 字典的默认值
     */
    String dictDefaultValue() default "";
}
