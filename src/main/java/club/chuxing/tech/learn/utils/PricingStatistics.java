package club.chuxing.tech.learn.utils;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import ch.hsr.geohash.WGS84Point;
import ch.hsr.geohash.util.VincentyGeodesy;

/**
 * Created by xuezhangying on 4/5/16.
 */
//直线距离，1.3倍直线距离，导航距离
public class PricingStatistics {
    private static final Logger logger = LoggerFactory.getLogger(PricingStatistics.class);
    public static final int GROUPS = 3;

    public static void main(String[] args) {
        double start = System.currentTimeMillis();
        BufferedReader reader = null;
        String filename = "/Users/xuezhangying/Downloads/尹非凡_2016_04_05_18_37_34__2016_04_05_19_13_22";
        try {
            int[][] result = new int[20][GROUPS];
            reader = new BufferedReader(new FileReader(filename));
            String line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] params = line.split("\t");
                if (params.length != 5) {
                    logger.warn("参数读取异常, 参数个数为{}", params.length);
                    break;
                }
                int[] nums = convert(params);
                DeliveryData data = new DeliveryData(nums);
                int[] differs = data.getDiffer3();
                for (int i = 0; i < differs.length; i++) {
                    for (int j = -10; j < 10; j++) {
                        if (differs[i] == j) {
                            if (j >= 0) {
                                result[j][i]++;
                            } else {
                                result[10 - j][i]++;
                            }
                            break;
                        }
                    }
                }
            }

            for (int i = 0; i < result.length; i++) {
                for (int j = 0; j < result[0].length; j++) {
                    System.out.print(((int)((double)result[i][j] / result[1][GROUPS - 1] * 10000)) / 10000D + " ");
                }
                System.out.println();
            }

        } catch (FileNotFoundException ex) {
            logger.info("未找到文件{}", filename);
        } catch (IOException ex) {
            logger.info("IO操作异常");
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException ex) {
                logger.info("未能正确关闭reader");
            }
        }

        logger.info("执行耗时{}ms!", System.currentTimeMillis() - start);
    }

    public static int[] convert(String[] params) {
        int[] nums = new int[params.length];
        for (int i = 0; i < params.length; i++) {
            nums[i] = NumberUtils.toInt(params[i]);
        }
        return nums;
    }

    public static class DeliveryData {
        public int reclng;
        public int reclat;
        public int sendlng;
        public int sendlat;
        public int delivery_dist;
        public int direct_dist;

        public DeliveryData(int[] params) {
            this.reclng = params[0];
            this.reclat = params[1];
            this.sendlng = params[2];
            this.sendlat = params[3];
            this.delivery_dist = params[4];
            setDirect_dist();
        }

        public DeliveryData(int reclng, int reclat, int sendlng, int sendlat, int delivery_dist) {
            this.reclng = reclng;
            this.reclat = reclat;
            this.sendlng = sendlng;
            this.sendlat = sendlat;
            this.delivery_dist = delivery_dist;
            setDirect_dist();
        }

        public void setDirect_dist() {
            WGS84Point rec = new WGS84Point(reclat / 1e+6, reclng / 1e+6);
            WGS84Point send = new WGS84Point(sendlat / 1e+6, sendlng / 1e+6);
            this.direct_dist = (int)VincentyGeodesy.distanceInMeters(rec, send);
        }

        /*
        * differ between direct_dist, 1.3 * direct_dist, delivery_dist
        * */
        public int[] getDiffer() {
            int a = getMoneyByDist((int)(this.direct_dist * 1.3));
            int b = getMoneyByDist((int)(this.direct_dist * 1.4));
            int c = getMoneyByDist(this.delivery_dist);
            a = a == c ? 0 : 1;
            b = b == c ? 0 : 1;
            c = 1;
            return new int[]{a, b, c};
        }

        public int[] getDiffer2() {
            int a = getMoneyByDist(this.direct_dist);
            int b = getMoneyByDist((int)(this.direct_dist * 1.3));
            int c = getMoneyByDist(this.delivery_dist);
            a = Math.abs(a - c);
            b = Math.abs(b - c);
            c = 1;
            return new int[]{a, b, c};
        }

        public int[] getDiffer3() {
            int a = getMoneyByDist((int)(this.direct_dist * 1.3));
            int b = getMoneyByDist((int)(this.direct_dist * 1.4));
            int c = getMoneyByDist(this.delivery_dist);
            a = a - c;
            b = b - c;
            c = 1;
            return new int[]{a, b, c};
        }

        public int getMoneyByDist(int dist) {
            if (dist < 2000) {
                return 5;
            } else {
                return 5 + (dist - 2000) / 1000 + 1;
            }
        }
    }
}
