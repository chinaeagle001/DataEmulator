package club.chuxing.tech.learn.statistic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by xuezhangying on 4/13/16.
 */
public class AreaConvert {
    private static final Logger logger = LoggerFactory.getLogger(AreaConvert.class);

    public static final String AREA_FILE = "/Users/xuezhangying/Downloads/xx/zhongbao_poi_2_ziying/area_ziying";
    public static final String POINT_FILE = "/Users/xuezhangying/Downloads/xx/zhongbao_poi_2_ziying/poi_zhongbao";
    public static final String AREA_FILE2 = "/Users/xuezhangying/Downloads/xx/ziying_poi_2_zhongbao/area_zhongbao";
    public static final String POINT_FILE2 = "/Users/xuezhangying/Downloads/xx/ziying_poi_2_zhongbao/poi_ziying";

    public static List<ShippingArea> getShippingAreas(String filename) throws Exception {
        List<ShippingArea> shippingAreas = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                ShippingArea shippingArea = ShippingArea.of(line);
                shippingAreas.add(shippingArea);
            }
        }
        return shippingAreas;
    }

    public static List<PlatformPoi> getPlatformPois(String filename) throws Exception {
        List<PlatformPoi> platformPois = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                PlatformPoi platformPoi = PlatformPoi.of(line);
                platformPois.add(platformPoi);
            }
        }
        return platformPois;
    }

    public static List<StatisticResult> statistic(String poifile, String areafile) throws Exception {
        List<ShippingArea> areas = getShippingAreas(areafile);
        List<PlatformPoi> pois = getPlatformPois(poifile);

        List<StatisticResult> results = new ArrayList<>(pois.size());
        for (PlatformPoi poi : pois) {
            List<Long> areaIds = new ArrayList<>();
            for (ShippingArea area : areas) {
                if (area.polygon.contains(poi.point)) {
                    areaIds.add(area.area_id);
                }
            }
            StatisticResult result = new StatisticResult(poi.poi_id, areaIds);
            results.add(result);
        }
        return results;
    }


    public static void main(String[] args) {
        try {
            List<StatisticResult> results = statistic(POINT_FILE, AREA_FILE);
            List<StatisticResult> results2 = statistic(POINT_FILE2, AREA_FILE2);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter("/Users/xuezhangying/Downloads/xx/zhongbao_poi_2_ziying.txt"))) {
                write(writer, results);
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("/Users/xuezhangying/Downloads/xx/ziying_poi_2_zhongbao.txt"))) {
                write(writer, results2);
            }

        } catch (Exception ex) {
            logger.warn("ex:{}", ex);
        }

    }

    public static void write(BufferedWriter writer, List<StatisticResult> results) throws Exception {
        for (StatisticResult result : results) {
            writer.write(result.poi_id + " " + result.areaIds + "\n");
        }
    }

    public static class StatisticResult {
        public long poi_id;
        public List<Long> areaIds;

        public StatisticResult(long poi_id, List<Long> areaIds) {
            this.poi_id = poi_id;
            this.areaIds = areaIds;
        }

        @Override
        public String toString() {
            return "StatisticResult{" +
                    "poi_id=" + poi_id +
                    ", areaIds=" + areaIds +
                    '}';
        }
    }

}
