package club.chuxing.tech.learn.utils;

/**
 * Created by xuezhangying on 4/5/16.
 */
public class MathUtil {
    /*
    * 四舍五入至0.1
    * */
    public static double round(double d) {
        return (int)((d + 0.05) * 10) / 10D;
    }

    public static int[] add(int[] nums1, int[] nums2) {
        if (nums1.length != nums2.length) {
            throw new IllegalArgumentException("错误的输入参数列表");
        }
        for (int i = 0; i < nums1.length; i++) {
            nums1[i] += nums2[i];
        }
        return nums1;
    }
}
