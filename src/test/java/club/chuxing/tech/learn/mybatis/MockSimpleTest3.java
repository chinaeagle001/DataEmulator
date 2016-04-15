package club.chuxing.tech.learn.mybatis;

import mockit.Expectations;
import mockit.Mocked;
import mockit.NonStrictExpectations;
import mockit.Verifications;
import org.junit.Test;

/**
 * Created by xuezhangying on 3/10/16.
 */
public class MockSimpleTest3 {
    @Mocked
    Foo foo;

    @Test
    public void testExpectations()throws Exception{
        new Expectations() {
            {
                new Foo();
                foo.say("hello");
                result="Yes";
            }
            {
                new Foo();
                foo.say("hello2");
                result="No";
            }
        };

        System.out.println(new Foo().say("hello"));
        System.out.println(new Foo().say("hello2"));

//        new Verifications(){
//            {
//                new Foo();
//                foo.say("hello");
//                times =1;
//
//                new Foo();
//                foo.say("hello2");
//                times=1;
//
//            }
//        };

    }

    class Foo {
        public String say(String something){
            return  something;
        }
    }
}