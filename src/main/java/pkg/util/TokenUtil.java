package pkg.util;

import com.jfinal.plugin.redis.Cache;
import com.jfinal.plugin.redis.Redis;
import pkg.model.User;

import java.util.UUID;

public class TokenUtil {
    public static String generateToken(User user){
        Cache auth = Redis.use("auth");
        String key = UUID.randomUUID().toString();
        auth.setex("jfinal:auth:token:"+key,7*24*60*60,user);
//        auth.setex("jfinal:auth:token:"+key,10*60,user);
        return key;
    }

    public static User verifyToken(String token){
        if(token==null) return null;

        Cache auth = Redis.use("auth");
        User val = auth.get("jfinal:auth:token:"+token);
        if(val==null) return null;

        User real = User.dao.findById(val.getId());
        if(!real.equals(val)) return null;

        return real;
    }
    public static void removeToken(String token){
        if(token==null) return ;
        Cache auth = Redis.use("auth");
        auth.expire("jfinal:auth:token:"+token,1);
    }
}
