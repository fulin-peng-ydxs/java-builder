package build.builder.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
/**
 * 构建结果
 *
 * @author peng_fu_lin
 * 2022-09-08 16:34
 */
@Getter
@AllArgsConstructor
public class BuildResult {

    private String buildName;

    private byte[] buildData;

}