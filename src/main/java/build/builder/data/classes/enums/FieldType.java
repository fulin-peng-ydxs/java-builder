package build.builder.data.classes.enums;
import java.util.Date;

public enum FieldType {


    STRING(String.class,"char,varchar"),
    DATE(Date.class,"datetime,date,timestamp"),
    INT(int.class,"int,bigint");

    public final Class<?> java;
    public final String basis;

    FieldType(Class<?> javaValue,String basisValue){
        this.java=javaValue;
        this.basis=basisValue;
    }

    public static  FieldType supportType(String basisValue){
        if (STRING.basis.contains(basisValue))
            return STRING;
        else if(DATE.basis.contains(basisValue))
            return DATE;
        else if(INT.basis.contains(basisValue))
            return INT;
        else
            return STRING;
    }


    @Override
    public String toString() {
        return  java.getSimpleName();
    }
}
