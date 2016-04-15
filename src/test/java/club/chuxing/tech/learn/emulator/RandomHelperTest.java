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
            RandomHelper.nextLong(3, 8);
        }
    }

    @Test
    public void testNextDouble() throws Exception {

    }

    @Test
    public void testNextString() throws Exception {

    }
}