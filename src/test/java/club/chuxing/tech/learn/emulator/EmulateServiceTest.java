package club.chuxing.tech.learn.emulator;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;

/**
 * Created by xuezhangying on 4/15/16.
 */
public class EmulateServiceTest {
    private static final Logger logger = LoggerFactory.getLogger(EmulateServiceTest.class);
    @Test
    public void testEmulate() throws Exception {
        try {
            EmulateService emulateService = new EmulateService("/Users/xuezhangying/documents/table");
            System.out.println(emulateService.emulate());
        } catch (Exception ex) {
            logger.info("Exception, ex:{}", ex);
        }
    }
}