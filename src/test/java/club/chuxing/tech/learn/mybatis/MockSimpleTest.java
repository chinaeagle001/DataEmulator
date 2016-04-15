package club.chuxing.tech.learn.mybatis;

import mockit.Injectable;
import mockit.Mock;
import mockit.MockUp;
import mockit.Mocked;
import org.junit.Test;

/**
 * Created by xuezhangying on 3/9/16.
*/


public class MockSimpleTest {
    @Injectable
    private Foo foo;
    @Test
    public void testMockUp() {
        new MockUp<Foo>() {
            @Mock
            public void say() {
                System.out.println("mock the interface");
            }
        };
        foo.say();

    }

    interface Foo {
        public void say();
    }

}

//public class MockSimpleTest {
//    @Injectable
//    private  Foo foo;
//
//    @Test
//    public void testMockUp() {
//        new MockUp<Foo>() {
//            @Mock
//            private void say() {
//                System.out.println("interface say");
//            }
//        };
//
//        foo.say();
//    }
//
//    interface Foo {
//        public void say();
//    }
//
//}
