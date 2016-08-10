package test;


import com.framework.common.util.MD5;

/**
 * Created with IntelliJ IDEA.
 *
 * @version 1.0
 * @author: 罗尧
 * @since 14-8-27 15:16
 * Email:johnny_lx@yahoo.com
 */
public class Test {

    public static boolean isNotNull(String value) {
        return value != null && value.trim().length() > 0;
    }


    public static void main(String[] args) {
//        MD5 md5 = new MD5();
//        System.out.println(md5.getMD5ofStr("123456"));

        String value = "";

        System.out.println(value.trim().length());

        System.out.println(Test.isNotNull(""));

    }
}
