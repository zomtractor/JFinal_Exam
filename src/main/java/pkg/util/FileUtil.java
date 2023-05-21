package pkg.util;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Random;

public class FileUtil {
    public static String DISK_PATH = "D:/public/jfinal";
    public static String HTTP_URL = "http://localhost:8081/jfinal";
    public static synchronized String saveImg(File file,String pathSuffix){
        if(file==null){
            return null;
        }
        try{
            String s = ""+System.currentTimeMillis();
            String path = DISK_PATH+pathSuffix+s+".jpg";
            FileInputStream fis = new FileInputStream(file);
            FileOutputStream fos = new FileOutputStream(path);
            IOUtils.copy(fis,fos);
            try{
                fos.close();
                fis.close();
            }catch (Exception e){
                e.printStackTrace();
            }
            return HTTP_URL+pathSuffix+s+".jpg";
        }catch (Exception e){
            return null;
        }
    }
}
