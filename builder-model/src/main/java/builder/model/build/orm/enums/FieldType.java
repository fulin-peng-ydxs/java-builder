package builder.model.build.orm.enums;

import lombok.AllArgsConstructor;
import lombok.ToString;

import java.util.Date;

@ToString
@AllArgsConstructor
public enum FieldType {

    STRING(String.class,"char,varchar"),

    DATE(Date.class,"datetime,date,timestamp"),

    INT(Integer.class,"int"),

    BIG_INT(Long.class,"bigint");

    public final Class<?> javaType;

    public final String dataBaseTypes;


    public static FieldType supportType(String dataBaseType){
        if (STRING.dataBaseTypes.contains(dataBaseType))
            return STRING;
        else if(DATE.dataBaseTypes.contains(dataBaseType))
            return DATE;
        else if(INT.dataBaseTypes.contains(dataBaseType))
            return INT;
        else if(BIG_INT.dataBaseTypes.contains(dataBaseType))
            return INT;
        else
            return null;
    }
}
