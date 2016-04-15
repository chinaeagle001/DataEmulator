package club.chuxing.tech.learn.utils;

import javax.sound.midi.SysexMessage;

/**
 * Created by xuezhangying on 3/24/16.
 */
public class PrintUtil {

    public static void print(Object...objects) {
        for (Object obj : objects) {
            System.out.print(obj + " ");
        }
    }

    public static void print(int...nums) {
        for (int num : nums) {
            System.out.print(num + " ");
        }
        System.out.println();
    }

    public static void print(double...nums) {
        for (double num : nums) {
            System.out.print(num + " ");
        }
        System.out.println();
    }
}
