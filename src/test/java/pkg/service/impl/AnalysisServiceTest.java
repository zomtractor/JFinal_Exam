package pkg.service.impl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pkg.bean.Result;
import pkg.config.DbConfig;
import pkg.config.RedisConfig;
import pkg.model.User;
import pkg.service.AnalysisService;
import pkg.util.UserHolder;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class AnalysisServiceTest {

    private AnalysisService service;
    private SimpleDateFormat sf;

    @Before
    public void init(){
        DbConfig.getDruid().start();
        DbConfig.getArp().start();
        RedisConfig.getRp().start();
        service = new AnalysisServiceImpl();
        sf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        User user = new User().set("id",14).set("is_manager",false).set("username","testUser11");
        UserHolder.saveUser(user);
    }
    @Test
    public void getDishChartByDate() throws Exception{
        Date d1 = sf.parse("2023.05.01 12:00:00");
        Date d2 = sf.parse("2023.05.21 12:00:00");
        Result res = service.getDishChartByDate(d1, d2);
        Assert.assertNotNull(res);
        System.out.println(res);
    }

    @Test
    public void getDishChartById() throws Exception {
        long id = 1;
        Date start = sf.parse("2023.05.01 12:00:00");
        Date end = sf.parse("2023.05.21 12:00:00");
        Result res = service.getDishChartById(id, start, end);
        Assert.assertNotNull(res);
        System.out.println(res);
    }

    @Test
    public void getUserChartById() {
        long id = 1;
        Result res = service.getUserChartById(id);
        Assert.assertNotNull(res);
        System.out.println(res);
    }

    @Test
    public void getUserChartByDate() throws Exception{
        long id = 1;
        Date start = sf.parse("2023.05.01 12:00:00");
        Date end = sf.parse("2023.05.21 12:00:00");
        Result res = service.getUserChartByDate(id, start, end);
        Assert.assertNotNull(res);
        System.out.println(res);
    }

    @Test
    public void getDishChartByRedis() {
        Result res = service.getDishChartByRedis();
        Assert.assertNotNull(res);
        System.out.println(res);
    }

    //@Test
    public void publishNewTest() throws Exception{
        Date start = sf.parse("2023.05.21 12:00:00");
        Result res = service.publishNewTest(start);
        Assert.assertNotNull(res);
        System.out.println(res);
    }

    @Test
    public void getHistoryById() {
        Result res = service.getHistoryById();
        Assert.assertNotNull(res);
        System.out.println(res);
    }

    @Test
    public void postForm() {
        long id = 14;
        List<Long> ls = Arrays.asList(3L, 4L, 5L, 6L, 7L);
        Result result = service.postForm(id, ls);
        Assert.assertNotNull(result);
        System.out.println(result);
    }

    @Test
    public void isPosted() {
        long id = 14;
        Result result = service.isPosted(id);
        Assert.assertNotNull(result);
        System.out.println(result);
    }
}
