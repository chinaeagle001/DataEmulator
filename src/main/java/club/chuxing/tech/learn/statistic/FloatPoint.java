package club.chuxing.tech.learn.statistic;

import java.awt.*;

/**
 * Created by xuezhangying on 4/13/16.
 */
public class FloatPoint {
    private static final int SCALE = 1000000;
    public double x;
    public double y;
    public Point getPoint() {
        return new Point((int)(x * SCALE), (int)(y * SCALE));
    }

    @Override
    public String toString() {
        return "FloatPoint{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
