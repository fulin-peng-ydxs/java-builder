package build.builder.data.classes.enums;
import build.builder.data.classes.meta.BusinessClass;

import java.util.Date;
public enum FieldType {


    STRING(String.class,"char,varchar"),
    DATE(Date.class,"datetime,date,timestamp"),
    INT(Integer.class,"int,bigint"),
    BUSINESS(BusinessClass.class,"business");

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
            return BUSINESS;
    }

    @Override
    public String toString() {
        return  java.getSimpleName();
    }
}
