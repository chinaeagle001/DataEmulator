package club.chuxing.tech.learn.redis;

/**
 * Created by xuezhangying on 3/23/16.
 */

import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Map;

public class TestRedis {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("localhost");
        print(jedis.ping());
        print();

        jedis.set("name", "lee");
        print(jedis.get("name"));
        print();

        jedis.del("vector");
        jedis.lpush("vector", "bbb");
        jedis.lpush("vector", "aaa");
        jedis.rpush("vector", "ccc");
        List<String> list = jedis.lrange("vector", 0, -1);
        for (int i = 0; i < list.size(); i++) {
            print(list.get(i));
        }
        print();

        jedis.hset("person1", "name", "Lee");
        jedis.hset("person1", "age", "12");
        jedis.hset("person1", "address", "beijing");

        Map<String, String> mymap = jedis.hgetAll("person1");
        for (Map.Entry<String, String> entry : mymap.entrySet()) {
            print(entry.getKey() + " " + entry.getValue());
        }
    }

    public static void print(Object...objects) {
        for (Object obj: objects) {
            System.out.print(obj.toString() + " ");
        }
        System.out.println();
    }
}
