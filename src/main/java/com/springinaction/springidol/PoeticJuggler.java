package com.springinaction.springidol;

/**
 * Created by xuezhangying on 3/13/16.
 */
public class PoeticJuggler extends Juggler {
    private Poem poem;
    public PoeticJuggler(Poem poem) {
        this.poem = poem;
    }

    public PoeticJuggler(int beanBags, Poem poem) {
        super(beanBags);
        this.poem = poem;
    }

    public void perform() throws PerformanceException {
        super.perform();
        System.out.println("While reciting...");
        poem.recite();
    }
}
