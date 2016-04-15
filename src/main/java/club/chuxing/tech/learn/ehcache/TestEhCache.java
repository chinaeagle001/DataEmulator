package club.chuxing.tech.learn.ehcache;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.UserManagedCache;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.UserManagedCacheBuilder;

import static club.chuxing.tech.learn.utils.PrintUtil.*;

/**
 * Created by xuezhangying on 3/24/16.
 */

public class TestEhCache {
    public static void main(String[] args) {
        //Managed Cache
        CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder().withCache("preConfigured",
                CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, String.class)).build(true);

        Cache<Long, String> name = cacheManager.createCache("name",
                CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, String.class).build());
        name.put(1L, "first one");
        print(name.get(1L));

        Cache<Long, String> aaa = cacheManager.getCache("preConfigured", Long.class, String.class);
        aaa.put(1L, "The First");
        print(aaa.get(1L));

        cacheManager.removeCache("preConfigured");
        cacheManager.close();

        //User Managed Cache
        UserManagedCache<String, Long> userManagedCache = UserManagedCacheBuilder.newUserManagedCacheBuilder(
                String.class, Long.class).build(true);
        userManagedCache.put("lee", 1213L);
        System.out.println(userManagedCache.get("lee"));
        userManagedCache.close();
    }
}
