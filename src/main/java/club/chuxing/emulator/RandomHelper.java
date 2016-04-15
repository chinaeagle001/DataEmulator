package club.chuxing.emulator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class RandomHelper {
    public static Random random = new Random(47);
    public static int nextInt(int from, int to) throws Exception {
        int flag = compare(from, to);
        if (flag == 0) {
            return from;
        } else if (flag == 1) {
            throw new InvalidRangeParamException(from, to);
        } else {
            return random.nextInt(to - from) + from;
        }
    }

    public static long nextLong(long from, long to) throws Exception {
        int flag = compare(from, to);
        if (flag == 0) {
            return from;
        } else if (flag == 1) {
            throw new InvalidRangeParamException(from, to);
        } else {
            long num = random.nextLong();
            num = num >= 0 ? num : - num;
            return num % (to - from) + from;
        }
    }

    public static double nextFloat(float from, float to) throws Exception {
        int flag = compare(from, to);
        if (flag == 0) {
            return from;
        } else if (flag == 1) {
            throw new InvalidRangeParamException(from, to);
        } else {
            return random.nextFloat() * (to - from) + from;
        }
    }

    public static double nextDouble(double from, double to) throws Exception {
        int flag = compare(from, to);
        if (flag == 0) {
            return from;
        } else if (flag == 1) {
            throw new InvalidRangeParamException(from, to);
        } else {
            return random.nextDouble() * (to - from) + from;
        }
    }

    public static <T> T nextValue(Set<T> values) {
        List<T> list = new ArrayList<>(values);
        return nextValue(list);
    }

    public static <T> T nextValue(List<T> values) {
        if(values == null || values.size() == 0) {
            return null;
        }
        return values.get(random.nextInt(values.size()));
    }

    private static int compare(double from, double to) {
        if (Math.abs(from - to) < 1e-6) {
            return 0;
        } else {
            return from - to < 0 ? -1 : 1;
        }
    }

    static class InvalidRangeParamException extends Exception {
        public InvalidRangeParamException(String info) {
            super(info);
        }

        public InvalidRangeParamException(double from, double to) {
            super("Invalid range param: [from:" + from + ", to:" + to + "]");
        }
    }
}
