package webapps.MOrangeCheck.com.Bean;

/**
 * Created by ppg777 on 2017/3/3.
 */

public class FileBean {

    String filePath;
    String fileName;
    String fileSize;
    /**
     * 后缀名
     */
    String filesuffix;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilesuffix() {
        return filesuffix;
    }

    public void setFilesuffix(String filesuffix) {
        this.filesuffix = filesuffix;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }
}
