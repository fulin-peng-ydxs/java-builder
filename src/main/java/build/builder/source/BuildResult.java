package build.builder.source;
import lombok.Data;
import java.io.OutputStream;
import java.util.List;

/**构建结果集
 * @author peng_fu_lin
 * 2022-09-05 09:46
 */
@Data
public class BuildResult {

    private List<Byte[]> buildBytes;

    private List<OutputStream> outputStreams;
}