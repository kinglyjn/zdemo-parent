package encrypt;

import org.junit.Test;

import java.util.Base64;

/**
 * @Author KJ
 * @Date 2020-03-26 10:30 PM
 * @Description
 */
public class Base64Demo {

    private Base64.Encoder encoder = Base64.getEncoder();
    private Base64.Decoder decoder = Base64.getDecoder();

    /**
     * base64 编解码测试
     */
    @Test
    public void test01() {
        String cipher = encoder.encodeToString("123456".getBytes());
        System.out.println("base64密文是：" + cipher);

        String src = new String(decoder.decode(cipher));
        System.out.println("解码原文是：" + src);
    }

}
