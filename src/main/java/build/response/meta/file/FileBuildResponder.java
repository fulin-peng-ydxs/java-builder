package build.response.meta.file;

import build.builder.data.BuildResult;
import build.response.exception.BuildResponseException;
import build.response.meta.BuildResponder;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
/**
 * 文件构建处理器
 *
 * @author peng_fu_lin
 * 2022-09-19 11:24
 */
public class FileBuildResponder extends BuildResponder {

    /**默认的构建路径:系统的桌面路径*/
    protected String defaultBuildPath=FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath();

    @Override
    public void buildResponse(OutputStream outputStream, List<BuildResult> buildResults) throws BuildResponseException {
        try {
            for (BuildResult buildResult : buildResults) {
                if(outputStream==null)
                    outputStream = new FileOutputStream(defaultBuildPath + File.separator +
                            buildResult.getBuildName());
                response(outputStream,buildResult);
                outputStream.close();
            }
        } catch (Exception e) {
            if(outputStream!=null) {
                try {
                    outputStream.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            throw new BuildResponseException("The fileBuildResponder run exception",e);
        }
    }

    protected void response(OutputStream outputStream,BuildResult buildResult) throws IOException {
        outputStream.write(buildResult.getBuildData());
    }

    @Override
    public boolean isSupported(OutputStream outputStream) {
        return outputStream instanceof FileOutputStream || outputStream ==null;
    }
}