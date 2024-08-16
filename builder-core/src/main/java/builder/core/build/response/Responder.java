package builder.core.build.response;

import lombok.Getter;
import lombok.Setter;

/**
 * 响应器
 * author: pengshuaifeng
 * 2023/9/17
 */
@Getter
@Setter
public abstract class Responder {

    //响应根路径
    protected String rootPath;

    public Responder(){

    }

    public Responder(String rootPath){
        this.rootPath=rootPath;
    }

    /**
     *响应文件
     *2023/9/2 09:07
     *@author pengshuaifeng
     *@param fileContent 文件内容
     *@param fileName 文件名
     *@param path 输出路径
     */
    public abstract void execute(Object fileContent,String fileName,String path);

    /**
     *响应文件
     *2023/9/2 09:07
     *@author pengshuaifeng
     *@param fileContent 文件内容
     *@param fileName 文件名
     *@param path 绝对路径
     */
    public abstract void executeAbsolutely(Object fileContent,String fileName,String path);
}
