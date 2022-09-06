package build.builder.model.classes;
import java.util.Date;

public enum FieldType {

    STRING(String.class,"char,varchar"),
    DATE(Date.class,"datetime,date,timestamp"),
    INT(Date.class,"int,bigint");

    public final Class javaValue;
    public final String basisValue;

    FieldType(Class javaValue, String basisValue){
        this.javaValue=javaValue;
        this.basisValue=basisValue;
    }

    public static  FieldType supportType(String basisValue){
        if (STRING.basisValue.contains(basisValue))
            return STRING;
        else if(DATE.basisValue.contains(basisValue))
            return DATE;
        else if(INT.basisValue.contains(basisValue))
            return INT;
        else
            return STRING;
    }
}
