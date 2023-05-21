package pkg.config;

import com.jfinal.plugin.activerecord.cache.ICache;
import com.jfinal.plugin.redis.Cache;
import com.jfinal.plugin.redis.Redis;
import com.jfinal.plugin.redis.RedisPlugin;

import java.util.Set;

public class RedisConfig implements ICache {
    private static RedisPlugin redisPlugin;

    public static String CACHE_PF = "jfinal:cache:";

    public static RedisPlugin getRp(){
        if(redisPlugin==null) {
            redisPlugin = new RedisPlugin("auth", "localhost");
        }
        return redisPlugin;
    }

    @Override
    public <T> T get(String cacheName, Object key) {
        return Redis.use("auth").get(CACHE_PF+cacheName+":"+key);
    }

    @Override
    public void put(String cacheName, Object key, Object value) {
        Redis.use("auth").setex(CACHE_PF+cacheName+":"+key,3,value);
    }

    @Override
    public void remove(String cacheName, Object key) {
        Redis.use("auth").expire(CACHE_PF+cacheName+":"+key,1);
    }

    @Override
    public void removeAll(String cacheName) {
        Cache redis = Redis.use("auth");
        Set<String> keys = redis.keys(CACHE_PF + cacheName + ":*");
        for(String i:keys) redis.expire(i,1);
    }
}
