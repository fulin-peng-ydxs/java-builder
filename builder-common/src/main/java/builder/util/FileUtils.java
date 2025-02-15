package builder.util;


import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.FileOutputStream;

/**
 * 文件工具
 * author: pengshuaifeng
 * 2023/8/31
 */
public class FileUtils {

    /**
     *文件输出
     *2023/9/2 09:14
     *@author pengshuaifeng
     *@param content 字节数组
     *@param fileName 文件名
     *@param path 文件目录
     */
    public static void flush(byte[] content,String fileName,String path){
        try(FileOutputStream outputStream = new FileOutputStream(createFile(path,fileName))){
            outputStream.write(content);
        } catch (Exception e) {
           throw new RuntimeException("文件输出异常：",e);
        }
    }

    /**
     * 创建文件
     * 2023/9/8 22:34
     * @author pengshuaifeng
     */
    public static File createFile(String mkdirPath,String fileName){
        File mkdir = new File(mkdirPath);
        if(!mkdir.exists()){
            try {
                mkdir.mkdirs();
            } catch (Exception e) {
                throw new RuntimeException("文件目录创建异常："+mkdirPath,e);
            }
        }
        String filePath = mkdir + File.separator + fileName;
        File file = new File(filePath);
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (Exception e) {
                throw new RuntimeException("文件创建失败："+filePath,e);
            }
        }
        return file;
    }

    /**
     * 获取系统当前用户目录路径
     * 2023/12/30 13:37
     * @author pengshuaifeng
     */
    public static String getSystemHomePath(){
        File homeDirectory = FileSystemView.getFileSystemView() .getHomeDirectory();
        return homeDirectory.getAbsolutePath();
    }


    /**
     * 路径拼接
     * 2024/8/8 下午3:32
     * @author fulin-peng
     */
    public static String pathSeparator(String rootPath,String path){
        String separator = File.separator;
        if(rootPath.endsWith(separator) && path.startsWith(separator))
            return rootPath+ StringUtils.substring(path,separator,null,false,true);
        else if(!rootPath.endsWith(separator) && !path.startsWith(separator))
            return rootPath + File.separator + path;
        else return rootPath+path;
    }
}
