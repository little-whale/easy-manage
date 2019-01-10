package com.github.littlewhale.easymanage.modules.commom.util;

import com.vip.vjtools.vjkit.number.RandomUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.Sha1Hash;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * @author cjp
 * @date 2019/1/9
 */
public class ShiroUtil {

    /**
     * 加密次数-循环加密
     */
    private static final int CUSTOMER_ITERATIONS = 6;

    /**
     * 盐值长度-随机字母或数字
     */
    private static final int SALT_LENGTH = 8;

    /**
     * 生成随机盐值
     *
     * @return
     */
    public static String salt() {
        return RandomUtil.randomStringFixLength(SALT_LENGTH);
    }

    /**
     * SHA256 加密
     *
     * @param source
     * @param salt
     * @return
     */
    public static String sha256(String source, String salt) {
        ByteSource byteSalt = ByteSource.Util.bytes(salt);
        SimpleHash result = new Sha256Hash(source, byteSalt, CUSTOMER_ITERATIONS);
        return result.toString();
    }

    /**
     * SHA1 加密
     *
     * @param source
     * @param salt
     * @return
     */
    public static String sha1(String source, String salt) {
        ByteSource byteSalt = ByteSource.Util.bytes(salt);
        SimpleHash result = new Sha1Hash(source, byteSalt, CUSTOMER_ITERATIONS);
        return result.toString();
    }

    /**
     * MD5 加密
     *
     * @param source
     * @param salt
     * @return
     */
    public static String md5(String source, String salt) {
        ByteSource byteSalt = ByteSource.Util.bytes(salt);
        SimpleHash result = new Md5Hash(source, byteSalt);
        return result.toString();
    }

    public static void main(String[] args) {
        String password = "123456";
        for (int i = 0; i < 2; i++) {
            String salt = salt();
            System.out.println("salt:" + salt);
            System.out.println("md5:" + md5(password, salt));
            System.out.println("sha1:" + sha1(password, salt));
            System.out.println("sha256:" + sha256(password, salt));
            System.out.println("---------------------------------");
        }

    }

}
