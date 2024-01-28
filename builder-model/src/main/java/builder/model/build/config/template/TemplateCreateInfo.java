package builder.model.build.config.template;

import lombok.*;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * 模版创建信息
 * author: pengshuaifeng
 * 2023/9/23
 */
@Data
public class TemplateCreateInfo {

    private Date date=new Date();

    private String userName="auto-builder";

    //TODO 可替换成时间工具类
    private final  SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 创建时间输出
     * 2023/9/23 20:37
     * @author pengshuaifeng
     */
    public String getDateToFormat(){
        return simpleDateFormat.format(date);
    }

    public String getDateToFormat(String format){
        return new SimpleDateFormat(format).format(date);
    }
}
