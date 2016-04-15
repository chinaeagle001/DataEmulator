package club.chuxing.tech.learn.statistic;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.Polygon;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuezhangying on 4/13/16.
 */
public class ShippingArea {
    public static final Logger logger = LoggerFactory.getLogger(ShippingArea.class);
    public long area_id;
    public Polygon polygon;
    public ShippingArea(long area_id, Polygon polygon) {
        this.area_id = area_id;
        this.polygon = polygon;
    }

    public static ShippingArea of(String line) {
        String[] areaIdAndPoints = line.split("\t");
        if (areaIdAndPoints.length != 2) {
            logger.error("文件格式错误!");
            return null;
        }
        long area_id = NumberUtils.toLong(areaIdAndPoints[0]);
        List<FloatPoint> pointList = JSON.parseArray(areaIdAndPoints[1], FloatPoint.class);
        return new ShippingArea(area_id, getPolygon(convert(pointList)));
    }

    public static List<Point> convert(List<FloatPoint> points) {
        List<Point> resultPoints = new ArrayList<>(points.size());
        for (FloatPoint floatPoint : points) {
            resultPoints.add(floatPoint.getPoint());
        }
        return resultPoints;
    }

    private static Polygon getPolygon(List<Point> points) {
        int[] xpoints = new int[points.size()];
        int[] ypoints = new int[points.size()];
        for (int i = 0; i < points.size(); i++) {
            xpoints[i] = points.get(i).x;
            ypoints[i] = points.get(i).y;
        }
        return new Polygon(xpoints, ypoints, points.size());
    }

    @Override
    public String toString() {
        return "ShippingArea{" +
                "area_id=" + area_id +
                ", polygon=" + polygon +
                '}';
    }
}
