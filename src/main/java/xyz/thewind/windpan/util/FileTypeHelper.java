package xyz.thewind.windpan.util;

import java.io.File;

public class FileTypeHelper {
    public static String getFileType(File file){
        if(file.isDirectory()){
            return "dir";
        }
        String fileName=file.getName();
        if(!fileName.contains(".")||fileName.charAt(fileName.length()-1)=='.'){
            return "file";
        }
        return fileName.substring(fileName.lastIndexOf(".")+1);
    }
}
