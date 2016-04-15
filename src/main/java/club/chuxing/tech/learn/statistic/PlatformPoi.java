package club.chuxing.tech.learn.statistic;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;

/**
 * Created by xuezhangying on 4/13/16.
 */
public class PlatformPoi {
    private static final Logger logger = LoggerFactory.getLogger(PlatformPoi.class);
    public long poi_id;
    public Point point;

    public PlatformPoi(long poi_id, Point point) {
        this.poi_id = poi_id;
        this.point = point;
    }

    public static PlatformPoi of(String line) {
        String[] words = line.split("\t");
        if (words.length != 3) {
            logger.error("文件格式错误");
        }
        long areaId = NumberUtils.toLong(words[0]);
        int x = NumberUtils.toInt(words[2]);
        int y = NumberUtils.toInt(words[1]);
        return new PlatformPoi(areaId, new Point(x, y));
    }
}
