package build.builder.data.classes.enums;

public enum MethodType {

    BASIS(""),

    ABSTRACT("abstract"),

    STATIC("static");

    public final String value;

    MethodType(String value){
        this.value=value;
    }


    @Override
    public String toString() {
        return value;
    }
}
