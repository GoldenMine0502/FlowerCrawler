package kr.goldenmine.util;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.Base64;

public class CipherUtil {
    public static byte[] createChecksum(File file) throws Exception {
        InputStream fis =  new FileInputStream(file);

        byte[] buffer = new byte[1024];
        MessageDigest complete = MessageDigest.getInstance("MD5");
        int numRead;

        do {
            numRead = fis.read(buffer);
            if (numRead > 0) {
                complete.update(buffer, 0, numRead);
            }
        } while (numRead != -1);

        fis.close();
        return complete.digest();
    }

    public static String getMD5Checksum(File file) throws Exception {
        byte[] b = createChecksum(file);
        StringBuilder result = new StringBuilder();

        for (int i=0; i < b.length; i++) {
            result.append(Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring(1));
        }
        return result.toString();
    }

    public static String getBase64FromImage(File file) throws IOException {
        byte[] fileContent = FileUtils.readFileToByteArray(file);

        return Base64.getEncoder().encodeToString(fileContent);
    }
}
