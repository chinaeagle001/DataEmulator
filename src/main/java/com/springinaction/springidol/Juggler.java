package com.springinaction.springidol;

/**
 * Created by xuezhangying on 3/13/16.
 */
public class Juggler implements Performer {
    private int beanBags = 3;

    public Juggler() {

    }

    public Juggler(int beanBags) {
        this.beanBags = beanBags;
    }

    public void perform() throws PerformanceException {
        System.out.println("JUGGLING " + beanBags + " BEANBAGS");
    }

}
