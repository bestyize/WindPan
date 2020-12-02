package xyz.thewind.windpan.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncodeHelper {
    public static String fileToMd5(String path){
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            FileInputStream fis = new FileInputStream(path);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = fis.read(buffer)) != -1) {
                md5.update(buffer, 0, len);
            }
            fis.close();

            byte[] byteArray = md5.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : byteArray) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (IOException | NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return null;
    }

    public static String md5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b:array) {
                sb.append(Integer.toHexString((b & 0xFF)|0x100), 1, 3);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
