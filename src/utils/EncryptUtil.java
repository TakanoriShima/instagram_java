package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

public class EncryptUtil {
    public static String getWordEncrypt(String plain) {
        String ret = "";
        String salt = "6Ab3mtmG"; // salt例
        if(plain != null && !plain.equals("")) {
            byte[] bytes;
            String password = plain + salt;
            try {
                bytes = MessageDigest.getInstance("SHA-256").digest(password.getBytes());
                ret = DatatypeConverter.printHexBinary(bytes);
            } catch(NoSuchAlgorithmException ex) {}
        }

        return ret;
    }
}