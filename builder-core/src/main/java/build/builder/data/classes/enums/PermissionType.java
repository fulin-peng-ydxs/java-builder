package build.builder.data.classes.enums;

public enum PermissionType {

    PRIVATE("private"),

    PUBLIC("public"),

    PROTECTED("protected"),

    DEFAULT("default");

    public final String value;

    PermissionType(String value){
        this.value=value;
    }


    @Override
    public String toString() {
        return value;
    }
}
