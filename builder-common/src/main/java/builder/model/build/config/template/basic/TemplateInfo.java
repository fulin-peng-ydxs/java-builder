package builder.model.build.config.template.basic;

import builder.util.DateUtils;
import lombok.Data;

import java.util.Date;
/**
 * 模版信息
 * author: pengshuaifeng
 * 2023/9/23
 */
@Data
public class TemplateInfo {

    public static final TemplateInfo defaultInstance=new TemplateInfo();

    //创建时间
    private Date date=new Date();

    //用户名
    private String userName="auto-builder";

    /**
     * 创建时间输出
     * 2023/9/23 20:37
     * @author pengshuaifeng
     */
    public String getDateToFormat(){
        return DateUtils.format(date);
    }

    public String getDateToFormat(String format){
        return DateUtils.format(date,format);
    }
}
