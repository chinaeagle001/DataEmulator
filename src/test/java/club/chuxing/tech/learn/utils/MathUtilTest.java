package club.chuxing.tech.learn.utils;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by xuezhangying on 4/5/16.
 */
public class MathUtilTest {

    @Test
    public void testRound() throws Exception {
        System.out.println(MathUtil.round(1.177));
        System.out.println(MathUtil.round(1.117));
        System.out.println(MathUtil.round(1.147));
        System.out.println(MathUtil.round(1.857));
    }
}