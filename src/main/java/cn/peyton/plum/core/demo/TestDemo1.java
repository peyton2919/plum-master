package cn.peyton.plum.core.demo;

import cn.peyton.plum.core.config.Location;

/**
 * <h4></h4>
 * <pre>
 * @author <a href="http://www.peyton.cn">peyton</a>
 * @mail <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @name cn.peyton.plum.core.demo.TestDemo1
 * @date 2023年10月14日 9:46
 * @version 1.0.0
 * </pre>
 */
public class TestDemo1 {



    public static void main(String[] args) {

        //Permission permission;
        //get("bbb");
        //Double sum = 2.0;
        //for (int i = 1; i < 65; i++) {
        //    sum = sum * 2;
        //}
        //System.out.println(sum);

        String product = Location.IMG_PRODUCT;
        String pathImgAd = Location.IMG_ADVERT;
        String pathImgAvatar = Location.IMG_AVATAR;
        System.out.println(product);
        System.out.println(pathImgAd);
        System.out.println(pathImgAvatar);
    }

    public static void get(String obj1) {
        String[] obj = obj1.split(",");
        for (String s : obj) {
            System.out.println(s);
        }

    }
}
