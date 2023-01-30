package build.source.data.bean;

import build.source.data.enums.NullType;
import build.source.data.enums.PrimaryKey;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**元实体项数据模型
 * 2022/9/5 0005-14:36
 * @author pengfulin
*/
@Setter
@Getter
public class BuildBeanItem {
    /** 项名称*/
    private String fieldName;
    /** 项类型*/
    private String fieldType;
    /** 项配置*/
    private ItemConfig itemConfig;

    @Setter
    @Getter
    @AllArgsConstructor
    public static class ItemConfig{
        /** 项长度*/
        private int fieldLength;
        /** 项注释*/
        private String fieldComment;
        /** 是否为空*/
        private NullType nullAble;
        /** 是否主键*/
        private PrimaryKey isPrimaryKey;
    }
}
