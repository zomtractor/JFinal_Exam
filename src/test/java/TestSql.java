import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.DaoTemplate;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.redis.Cache;
import com.jfinal.plugin.redis.Redis;
import com.jfinal.plugin.redis.RedisPlugin;
import org.junit.Before;
import org.junit.Test;
import pkg.bean.Pagination;
import pkg.bean.Result;
import pkg.config.RedisConfig;
import pkg.model.Dish;
import pkg.model.DishRate;
import pkg.model.User;
import pkg.model.UserRate;
import pkg.service.impl.AnalysisServiceImpl;
import pkg.service.impl.UserServiceImpl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class TestSql {
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
    public void insertUserTest() {
        Random random = new Random();
        for (int i = 0; i < 25; i++) {
            User user = new User();
            user.setUsername("testUser" + i);
            user.setPassword("121212121212a");
            user.setAge(i);
            user.setGender(random.nextBoolean() ? "男" : "女");
            user.setIsManager(false);
            user.save();
        }
    }

    @Test
    public void testSize() {
        System.out.println();
    }

    @Test
    public void testEnjoy() {
        DaoTemplate<User> template = User.dao.template("selectUserByName", "zs");
        System.out.println(template.findFirst());
    }

    @Test
    public void testEnjoy2() {
        User user = new User();
        //user.setUsername("%%");
        user.setAge(12);
        DaoTemplate<User> template = User.dao.template("selectUserByCondition", Kv.of("user", user));
        System.out.println(template.find());
    }

    @Test
    public void testPagination() {
        User user = new User();
        user.setUsername("");
        Pagination pagination = new Pagination();
        pagination.setPageSize(5);
        pagination.setCurrentPage(1);
        pagination.setData(user);
        UserServiceImpl userService = new UserServiceImpl();

        System.out.println(userService.getPaginate(pagination));
    }

    @Test
    public void testGetUser() {
        Result byId = new UserServiceImpl().getById(5);
        System.out.println(byId);
    }

    @Test
    public void insertDishTest() {
        Random random = new Random();
        for (int i = 0; i < 25; i++) {
            Dish dish = new Dish();
            dish.setDescription("test" + i);
            dish.setName("test" + i);
            dish.setPrice(new BigDecimal(0.1 * i + 1));
            dish.setPicture("http://localhost:8081/jfinal/dish/2.jpg");
            dish.save();
        }
    }

    @Test
    public void insetDishRateTest() throws Exception {
        Random random = new Random();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int i = 0; i < 25; i++) {
            DishRate dishRate = new DishRate();
            dishRate.setDishId(Math.abs(random.nextLong() % 25));
            dishRate.setDemand(random.nextInt(100));
            dishRate.setTime(format.parse(String.format("2023-04-0%d %s", i % 8, i % 2 == 0 ? "12:00:00" : "18:00:00")));
            dishRate.save();
        }
    }

    //    @Test
//    public void testChartUtil(){
//        List<Dish> list = Db.query("select d.*\n" +
//                "from dish_rate left join dish d on d.id = dish_rate.dish_id\n" +
//                "where d.demand is not null\n" +
//                "group by name\n");
//        List<String> name = Db.query("select d.name\n" +
//                "from dish_rate left join dish d on d.id = dish_rate.dish_id\n" +
//                "where d.demand is not null\n" +
//                "group by name\n");
//        System.out.println(list);
//        List<Integer> list2 = Db.query("select sum(dish_rate.demand)\n" +
//                "from dish_rate left join dish d on d.id = dish_rate.dish_id\n" +
//                "where d.demand is not null\n" +
//                "group by name\n");
//        ChartData chartData = ChartUtil.singleChartData("菜品", "数量", name, list2, list);
//        System.out.println(chartData);
//    }
    @Test
    public void insetUserRateTest() throws Exception {
        Random random = new Random();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int i = 0; i < 25; i++) {
            UserRate userRate = new UserRate();
            userRate.setUserId(Math.abs(random.nextLong() % 25));
            userRate.setDishId(Math.abs(random.nextLong() % 25));
            userRate.setTime(format.parse(String.format("2023-04-0%d %s", i % 8, i % 2 == 0 ? "12:00:00" : "18:00:00")));
            userRate.save();
        }
    }

    @Test
    public void testDb() throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<Record> records = Db.template("getDishAnalysisRecord",
                format.parse("2023-04-01 11:59:59"),
                format.parse("2023-04-04 18:00:00")).find();
        System.out.println(records);
    }

    @Test
    public void testRedisGet() {
        Cache redis = Redis.use("auth");
        Random random = new Random();
        for (int i = 1; i < 10; i++) {
            redis.setex("jfinal:dish_count:" + i, 600, Math.abs(random.nextInt(100)));
        }
//        AnalysisServiceImpl service = new AnalysisServiceImpl();
//        Result result = service.getDishChartByRedis();
//        System.out.println(result);
    }

    @Test
    public void generateMultipleData() throws Exception {
        Random random = new Random();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (long userId = 10; userId < 19; userId++) {
            for (int i = 1; i < 12; i++) {
                for (long dishId = 1; dishId < 10; dishId++) {
                    if (random.nextBoolean()) {
                        UserRate userRate = new UserRate();
                        userRate.setUserId(userId);
                        userRate.setDishId(dishId);
                        userRate.setTime(format.parse(String.format("2023-05-0%d %s", i % 8 + 1, i % 2 == 0 ? "12:00:00" : "18:00:00")));
                        userRate.save();
                    }
                }

            }
        }
    }

    @Test
    public void generateMultipleDishData() throws Exception {
        Random random = new Random();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int i = 1; i < 12; i++) {
            for (long dishId = 1; dishId < 10; dishId++) {
                if (random.nextBoolean()) {
                    DishRate dishRate = new DishRate();
                    dishRate.setDishId(dishId);
                    dishRate.setTime(format.parse(String.format("2023-05-0%d %s", i % 8 + 1, i % 2 == 0 ? "12:00:00" : "18:00:00")));
                    dishRate.setDemand(random.nextInt(100));
                    dishRate.save();
                }
            }
        }
    }
    @Test
    public void testSqlCache(){
        List<Dish> list = Dish.dao.template("selectEnabled").findByCache("cache", "enabled");
        //List<Dish> list = Dish.dao.template("selectEnabled").find();
        System.out.println(list);
    }
}
