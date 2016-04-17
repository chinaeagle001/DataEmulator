package club.chuxing.emulator;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by xuezhangying on 4/17/16.
 */
public class EmulateServiceTest {

    @Test
    public void testEmulate() throws Exception {
        EmulateService service = new EmulateService("/Users/xuezhangying/Documents/tableinfo");
        System.out.println(service.emulate());
    }

    @Test
    public void testEmulate1() throws Exception {
        EmulateService service = new EmulateService("/Users/xuezhangying/Documents/tableinfo");
        System.out.println(service.emulate(5));
    }
}