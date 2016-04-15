package club.chuxing.tech.learn.emulator;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by xuezhangying on 3/31/16.
 */
public class RandomHelperTest {

    @Test
    public void testNextInt() throws Exception {
        for (int i = 0; i < 100; i++) {
            RandomHelper.nextInt(3, 8);
        }
    }

    @Test
    public void testNextLong() throws Exception {
        for (int i = 0; i < 100; i++) {
            System.out.println(RandomHelper.nextLong(-30, -10));
        }
    }

    @Test
    public void testNextDouble() throws Exception {
        for (int i = 0; i < 100; i++) {
            System.out.println(RandomHelper.nextDouble(-30.1233332, -8.13232));
        }
    }

    @Test
    public void testNextString() throws Exception {

    }
}