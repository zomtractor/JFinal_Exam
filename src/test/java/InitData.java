import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.redis.Cache;
import com.jfinal.plugin.redis.Redis;
import com.jfinal.plugin.redis.RedisPlugin;
import org.junit.Before;
import org.junit.Test;
import pkg.bean.LoginForm;
import pkg.config.RedisConfig;
import pkg.model.Dish;
import pkg.model.DishRate;
import pkg.model.User;
import pkg.model.UserRate;
import pkg.service.AnalysisService;
import pkg.service.UserAuthService;
import pkg.service.UserService;
import pkg.service.impl.AnalysisServiceImpl;
import pkg.service.impl.UserAuthServiceImpl;
import pkg.service.impl.UserServiceImpl;
import pkg.util.TokenUtil;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class InitData {

    @Before
    public void init() {
        System.out.println("init");
        String jdbcUrlString = "jdbc:mysql://127.0.0.1:3306/jfinal_exam";// 数据库连接
        DruidPlugin druidPlugin = new DruidPlugin(jdbcUrlString, "root", "1234");  //数据库连接插件
        druidPlugin.start();
        ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
        arp.addMapping("dish", "id", Dish.class);
        arp.addMapping("dish_rate", "id", DishRate.class);
        arp.addMapping("user", "id", User.class);
        arp.addMapping("user_rate", "id", UserRate.class);
        arp.addSqlTemplate("pkg/model/user.sql");
        arp.addSqlTemplate("pkg/model/dish.sql");
        arp.addSqlTemplate("pkg/model/multiple.sql");
        RedisPlugin rp = new RedisPlugin("auth", "localhost");
        rp.start();
        arp.setCache(new RedisConfig());
        //arp.setBaseSqlTemplatePath("pkg/model");
        arp.start();
    }

    @Test
    public void addAdmin(){
        User user = User.dao.findById(1);
        System.out.println(user);
        Redis.use("auth").set("jfinal:auth:token:admin",user);
    }
    @Test
    public void getAdmin(){
        User user = User.dao.findById(1);
        System.out.println(user);
        User user2 = Redis.use("auth").get("jfinal:auth:token:admin");
        System.out.println(user2);
    }
    @Test
    public void addUserRate() throws Exception{
        Random random = new Random();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (long userId = 10; userId < 19; userId++) {
            for (int i = 1; i < 15; i++) {
                for(int j=0;j<2;j++)
                for (long dishId = 1; dishId < 10; dishId++) {
                    if (random.nextBoolean()) {
                        UserRate userRate = new UserRate();
                        userRate.setUserId(userId);
                        userRate.setDishId(dishId);
                        userRate.setTime(format.parse(String.format("2023-05-%d %s", i , j == 0 ? "12:00:00" : "18:00:00")));
                        userRate.save();
                    }
                }
            }
        }
    }
    @Test
    public void addDishRate() throws Exception{
        Random random = new Random();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int i = 1; i < 15; i++) {
            for(int j=0;j<2;j++)
            for (long dishId = 1; dishId < 10; dishId++) {
                if (random.nextBoolean()) {
                    DishRate dishRate = new DishRate();
                    dishRate.setDishId(dishId);
                    dishRate.setTime(format.parse(String.format("2023-05-%d %s", i, j == 0 ? "12:00:00" : "18:00:00")));
                    dishRate.setDemand(random.nextInt(100));
                    dishRate.save();
                }
            }
        }
    }
    @Test
    public void addCurrent() {
        Cache redis = Redis.use("auth");
        Random random = new Random();
        for (int i = 1; i < 11; i++) {
            redis.set("jfinal:dish_count:" + i,Math.abs(random.nextInt(100)));
        }
//        AnalysisServiceImpl service = new AnalysisServiceImpl();
//        Result result = service.getDishChartByRedis();
//        System.out.println(result);
    }

    @Test
    public void addHighConcurrencyUser() throws Exception{
        Random random = new Random();
        UserAuthService userAuthService = new UserAuthServiceImpl();
        Cache redis = Redis.use("auth");
        FileWriter fos = new FileWriter("D:/hctest.txt");
        for(int i=0;i<1000;i++){
//            User user = new User();
//            user.setUsername("HC"+i);
//            user.setName("hc"+i);
//            user.setPassword("abcd12345678");
//            user.setAge(Math.abs(random.nextInt(100)));
//            user.setAvatar("http://localhost:8081/jfinal/user/1684056536365.jpg");
//            user.setIsManager(false);
//            user.setGender(random.nextBoolean()?"男":"女");
//            user.save();
//            redis.set("jfinal:auth:token:"+"hc"+i,user);
            fos.write(String.format("HC%d,hc%d\n",i,i));
        }
        fos.close();
    }
    @Test
    public void publishVoute(){
        AnalysisService service = new AnalysisServiceImpl();
        Db.update("update dish set enable = 1");
        service.publishNewTest(new Date());
    }
}
