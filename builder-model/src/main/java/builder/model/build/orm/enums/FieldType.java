package builder.model.build.orm.enums;

import lombok.AllArgsConstructor;
import lombok.ToString;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@ToString
@AllArgsConstructor
public enum FieldType {

    STRING(String.class, Arrays.asList("char","varchar")),

    DATE(Date.class,Arrays.asList("datetime","date","timestamp")),

    INT(Integer.class, Collections.singletonList("int")),

    BIG_INT(Long.class, Collections.singletonList("bigint"));

    public final Class<?> javaType;

    public final List<String> dataBaseTypes;


    public static FieldType supportType(String dataBaseType){
        if (STRING.dataBaseTypes.contains(dataBaseType))
            return STRING;
        else if(DATE.dataBaseTypes.contains(dataBaseType))
            return DATE;
        else if(INT.dataBaseTypes.contains(dataBaseType))
            return INT;
        else if(BIG_INT.dataBaseTypes.contains(dataBaseType))
            return BIG_INT;
        else return STRING;
    }
}
