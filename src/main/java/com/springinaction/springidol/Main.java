package com.springinaction.springidol;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by xuezhangying on 3/13/16.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-idol.xml");
        Performer performer = (Performer)ctx.getBean("kenny");
        performer.perform();
    }

}
