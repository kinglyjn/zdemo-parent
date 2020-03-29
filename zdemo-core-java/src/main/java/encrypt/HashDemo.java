package encrypt;

import org.junit.Test;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Author KJ
 * @Date 2020-03-26 6:18 PM
 * @Description
 */
public class HashDemo {

    static enum HashType { // 散列类型
        MD5, SHA1;
    }

    /**
     * md5加密
     */
    @Test
    public void md5() throws NoSuchAlgorithmException {
        // 获取加密对象
        MessageDigest encryptor = MessageDigest.getInstance(HashType.MD5.name());

        // 加密
        byte[] cipherBytes = encryptor.digest("123456".getBytes());

        // 处理获得加密结果
        char[] chars = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
        StringBuffer sb = new StringBuffer();
        for (byte b : cipherBytes) {
            sb.append(chars[(b>>4) & 0xf]);
            sb.append(chars[b & 0xf]);
        }

        String result_32 = sb.toString();
        String result_16 = sb.substring(8, 24);
        System.out.println("32位md5加密密文：" + result_32);
        System.out.println("16位md5加密密文：" + result_16);
    }




    /**
     * sha加密
     */
    @Test
    public void sha() throws NoSuchAlgorithmException {
        // 获取加密对象
        MessageDigest encryptor = MessageDigest.getInstance(HashType.SHA1.name());

        // 加密
        byte[] cipherBytes = encryptor.digest("123456".getBytes());

        // 处理获得加密结果
        char[] chars = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
        StringBuffer sb = new StringBuffer();
        for (byte b : cipherBytes) {
            sb.append(chars[(b>>4) & 0xf]);
            sb.append(chars[b & 0xf]);
        }
        String result = sb.toString();
        System.out.println(result);

    }
}
