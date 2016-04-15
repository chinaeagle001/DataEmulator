package club.chuxing.tech.learn.jmockit;

import mockit.Expectations;
import mockit.Mocked;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by xuezhangying on 4/15/16.
 */
public class SimpleToolTest {

    @Mocked
    SimpleTool simpleTool;

    @Test
    public void testUpper() throws Exception {
        new Expectations() {
            {
                simpleTool.upper(anyString);
                result = "XXX";
            }
        };

        System.out.println(simpleTool.upper("fsdf"));
    }
}