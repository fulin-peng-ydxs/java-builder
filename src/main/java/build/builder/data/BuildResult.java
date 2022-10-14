package build.builder.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 构建结果
 *
 * @author peng_fu_lin
 * 2022-09-08 16:34
 */
@Getter
@Setter
@AllArgsConstructor
public class BuildResult {
    /**构建名*/
    private String buildName;
    /**构建数据*/
    private byte[] buildData;
    /**构建地址*/
    private String buildTarget;
}