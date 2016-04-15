package com.springinaction.springidol;

/**
 * Created by xuezhangying on 3/13/16.
 */
public class Sonnet29 implements Poem {
    private static String[] LINES = {
            "line1: --------",
            "line2: --------",
            "line3: --------",
            "line4: --------",
            "line5: --------",
    };

    public Sonnet29() {}

    public void recite() {
        for (int i = 0; i < LINES.length; i++) {
            System.out.println(LINES[i]);
        }
    }
}
