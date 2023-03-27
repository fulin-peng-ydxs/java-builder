package build.builder.data.busness;

/**
 * Rest风格方法
 *
 * @author peng_fu_lin
 * 2023-01-30 17:19
 */
public enum RestMethod {

    FIND("find"),
    DETAIL("detail"),
    UPDATE("update"),
    UPDATE_BATCH("updateBatch"),
    ADD("add"),
    ADD_BATCH("addBatch"),
    DELETE_BATCH("deleteBatch"),
    DELETE("delete");

    private String name;

    RestMethod(String methodName){
        this.name=methodName;
    }
}
