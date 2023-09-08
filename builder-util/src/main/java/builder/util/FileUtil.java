package builder.util;


import java.io.File;
import java.io.FileOutputStream;

/**
 * 文件工具
 * author: pengshuaifeng
 * 2023/8/31
 */
public class FileUtil {

    /**
     *文件输出
     *2023/9/2 09:14
     *@author pengshuaifeng
     *@param content 字节数组
     *@param fileName 文件名
     *@param path 文件目录
     */
    public static void flush(byte[] content,String fileName,String path){
        String fullPath = path + File.separator + fileName;
        try(FileOutputStream outputStream = new FileOutputStream(createFile(path,fullPath))){
            outputStream.write(content);
        } catch (Exception e) {
           throw new RuntimeException("文件输出异常："+fullPath,e);
        }
    }

    /**
     * 创建文件
     * 2023/9/8 22:34
     * @author pengshuaifeng
     */
    public static File createFile(String mkdirPath,String filePath){
        File mkdir = new File(mkdirPath);
        if(!mkdir.exists()){
            try {
                mkdir.mkdirs();
            } catch (Exception e) {
                throw new RuntimeException("文件输出异常："+filePath,e);
            }
        }
        File file = new File(filePath);
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (Exception e) {
                throw new RuntimeException("文件输出异常："+filePath,e);
            }
        }
        return file;
    }


}
