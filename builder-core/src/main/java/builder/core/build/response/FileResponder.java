package builder.core.build.response;


import builder.util.FileUtil;
import builder.util.StringUtil;

import java.io.File;
import java.nio.charset.StandardCharsets;

/**
 * 文件响应器
 * author: pengshuaifeng
 * 2023/9/2
 */
public class FileResponder extends Responder{

    public FileResponder(){
        rootPath = FileUtil.getSystemHomePath()+File.separator+"JavaBuilds";
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
        byte[] bytes = fileContent.toString().getBytes(StandardCharsets.UTF_8);
        //TODO 替换成日志输出
        String outPath = path == null ? rootPath : pathSeparator(rootPath,path);
        System.out.println("输出文件："+outPath+"："+fileName);
        FileUtil.flush(bytes,fileName, outPath);
    }


    public String pathSeparator(String rootPath,String path){
        String separator = File.separator;
        if(rootPath.endsWith(separator) && path.startsWith(separator))
            return rootPath+ StringUtil.substring(path,separator,null,false,true);
        else if(!rootPath.endsWith(separator) && !path.startsWith(separator))
            return rootPath + File.separator + path;
        else return rootPath+path;
    }

}
