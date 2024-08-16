package builder.core.build.response;


import builder.util.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.charset.StandardCharsets;

/**
 * 文件响应器
 * author: pengshuaifeng
 * 2023/9/2
 */
public class FileResponder extends Responder{

    private static final Logger log = LoggerFactory.getLogger(FileResponder.class);

    public FileResponder(){
        rootPath = FileUtils.getSystemHomePath()+File.separator+"JavaBuilds";
    }

    public FileResponder(String rootPath){
        super(rootPath);
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
        String outPath = path == null ? rootPath : FileUtils.pathSeparator(rootPath,path);
        executeAbsolutely(fileContent,fileName,outPath);
    }

    public void executeAbsolutely(Object fileContent,String fileName,String path){
        byte[] bytes = fileContent.toString().getBytes(StandardCharsets.UTF_8);
        log.info("输出文件：{}：{}",path,fileName);
        FileUtils.flush(bytes,fileName, path);
    }

}
