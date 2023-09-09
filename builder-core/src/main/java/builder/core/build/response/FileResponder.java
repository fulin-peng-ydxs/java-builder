package builder.core.build.response;


import builder.util.FileUtil;
import lombok.Data;

import java.io.File;
import java.nio.charset.StandardCharsets;

/**
 * 文件响应器
 * author: pengshuaifeng
 * 2023/9/2
 */
@Data
public class FileResponder {

    //响应根路径：默认为系统当前用户家目录
    private String rootPath= System.getProperty("user.home")+File.separator+"JavaBuilds";

    public FileResponder(){

    }

    public FileResponder(String rootPath){
        this.rootPath=rootPath;
    }

    /**
     *输出文件
     *2023/9/2 09:07
     *@author pengshuaifeng
     *@param fileContent 文件内容
     *@param fileName 文件名
     *@param path 输出路径
     */
    public void execute(Object fileContent,String fileName,String path){
        byte[] bytes = fileContent.toString().getBytes(StandardCharsets.UTF_8);
        //TODO 替换成日志输出
        String outPath = path == null ? rootPath : rootPath + File.separator + path;
        System.out.println("输出文件："+outPath+"："+fileName);
        FileUtil.flush(bytes,fileName, outPath);
    }
}
