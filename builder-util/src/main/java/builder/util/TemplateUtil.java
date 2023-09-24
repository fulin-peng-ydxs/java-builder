package builder.util;

import builder.model.build.config.BuildGlobalConfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
/**
 * 模板工具
 * @author peng_fu_lin
 * 2022-10-10 14:10
 */
public class TemplateUtil {

    private static final Map<String,String> templateStringCache=new HashMap<>();

    private static final Map<String,List<String>> templateLineCache=new HashMap<>();

    private static final Map<String,Map<String,String>> templateCloneCache=new HashMap<>();

    /**获取模板：获取并存入缓存中
     * 2022/10/10 0010-14:11
     * @author pengfulin
     * @return 返回模版字符串
    */
    public static String getTemplate(String path) {
        try {
            String template = templateStringCache.get(path);
            if(template==null){
                template = StringUtil.listToString(getTemplates(path));
                if(StringUtil.isNotEmpty(template))
                    templateStringCache.put(path,template);
            }
            return template;
        } catch (Exception e) {
            throw new RuntimeException("获取模版异常",e);
        }
    }

    /**
     * 获取模版：获取并存入缓存中
     * 2023/9/5 21:05
     * @author pengshuaifeng
     * @return  返回模版字符串集合
     */
    public static List<String> getTemplates(String path){
        try {
            List<String> lines = templateLineCache.get(path);
            if(lines==null){
                InputStream stream;
                if(!path.startsWith("file:"))
                    stream = TemplateUtil.class.getResourceAsStream(path.replace("classpath:", ""));
                else
                    stream=new FileInputStream(path);
                lines = StringUtil.fileToLines(new InputStreamReader(
                        Objects.requireNonNull(stream)), false, false, "\n");
                if(CollectionUtil.isNotEmpty(lines))
                    templateLineCache.put(path,lines);
            }
            return lines;
        }
        catch (Exception e) {
            throw new RuntimeException("获取模版异常",e);
        }
    }

    /**
     * 获取克隆模版
     * 2023/9/6 00:44
     * @author pengshuaifeng
     */
    public static Map<String, String> getCloneTemplates(String path){
        try {
            Map<String,String> cloneTemplate= templateCloneCache.get(path);
            if(cloneTemplate==null){
                String template = getTemplate(path);
                if (StringUtil.isNotEmpty(template)) {
                    cloneTemplate = JsonUtils.getObject(template, Map.class);
                    templateCloneCache.put(path,cloneTemplate);
                }
            }
            return cloneTemplate;
        }
        catch (Exception e) {
            throw new RuntimeException("获取克隆模版异常",e);
        }
    }

    /**
     * 获取模版克隆模型路径
     * 2023/9/6 00:43
     * @author pengshuaifeng
     */
    public static String getCloneTemplatePath(String path){
        String templateFileName = StringUtil.substring(path, File.separator, null, false, false);
        return path.replace(templateFileName, "clone" + File.separator +
                StringUtil.substring(templateFileName, null, ".", false, false) + ".json");
    }

    /**
     * 模版内容填充
     * 2023/9/5 21:21
     * @author pengshuaifeng
     * @param template 模版
     * @param paddingValues 模版填充
     */
    public static String paddingTemplate(String template,Map<String,String> paddingValues){
        paddingCreateInfo(paddingValues);
        for (Map.Entry<String, String> padding : paddingValues.entrySet()) {
            template=template.replace(padding.getKey(),padding.getValue());
        }
        return template;
    }

    public static String paddingTemplate(String template,String paddingKey,String paddingValue){
        return template.replace(paddingKey,paddingValue);
    }

    /**
     * 模版创建信息填充
     * 2023/9/23 19:22
     * @author pengshuaifeng
     */
    public static void paddingCreateInfo(Map<String,String> paddingValues){
        paddingValues.put("{date}", BuildGlobalConfig.templateCreateInfo
                .getDateToFormat());
        paddingValues.put("{author}",BuildGlobalConfig.templateCreateInfo.getUserName());
    }
}