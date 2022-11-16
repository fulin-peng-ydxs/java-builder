package build.builder.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 模板缓存工具
 *
 * @author peng_fu_lin
 * 2022-10-10 14:10
 */
public class TemplateCacheUtil {

    private static final Map<String,String> templateCache=new HashMap<>();

    /**获取模板
     * 2022/10/10 0010-14:11
     * @author pengfulin
    */
    public static String getTemplate(String path) throws IOException {
        String template = templateCache.get(path);
        if(template==null){
            InputStream stream;
            if(path.startsWith("classpath:"))
                stream = TemplateCacheUtil.class.getResourceAsStream(path.replace("classpath:", ""));
            else
                stream=new FileInputStream(path);
            template = StringBuildUtil.fileToString(new InputStreamReader(
                            Objects.requireNonNull(stream)), false, false, "\n");
            templateCache.put(path,template);
        }
        return template;
    }
}