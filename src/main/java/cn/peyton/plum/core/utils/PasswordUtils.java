package cn.peyton.plum.core.utils;

import java.io.Serializable;
import java.util.Random;

/**
 * <h3>Password 加密工具类</h3>
 * <pre>
 * @email: <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @create date: 2018/11/16 15:59
 * @author: <a href="http://www.peyton.cn">peyton</a>
 * @version: 1.0.0
 * </pre>
 */
public final class PasswordUtils implements Serializable {

    private final static String[] word = {
            "a","b","c","d","e","f","g","h","j",
            "k","m","n","p","q","r","s","t","u",
            "v","w","x","y","z","A","B","C","D",
            "E","F","G","H","J","K","M","N","P",
            "Q","R","S","T","U","V","W","X","Y", "Z"
    };

    private final static String[] num = {
            "2","3","4","5","6","7","8","9"
    };

    public static String randomPassword() {
        StringBuffer sb = new StringBuffer();
        Random random = new Random(System.currentTimeMillis());
        boolean flag = false;

        int length = random.nextInt(3) + 8;
        for (int i =0 ; i < length; i++) {
            if (flag) {
                sb.append(num[random.nextInt(num.length)]);
            } else {
                sb.append(word[random.nextInt(word.length)]);
            }
            flag = !flag;
        }
        return sb.toString();
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println(randomPassword());
        Thread.sleep(100);
        System.out.println(randomPassword());
        Thread.sleep(100);
        System.out.println(randomPassword());
        Thread.sleep(100);
        System.out.println(randomPassword());
    }
}
