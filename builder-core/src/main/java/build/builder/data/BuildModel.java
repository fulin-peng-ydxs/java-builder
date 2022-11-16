package build.builder.data;

import lombok.Getter;
import lombok.Setter;

/**
 * 构建模型
 *
 * @author peng_fu_lin
 * 2022-09-29 15:36
 */
@Getter
@Setter
public abstract class BuildModel {
    /**构建地址*/
    protected String target;
}