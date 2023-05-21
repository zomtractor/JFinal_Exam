package pkg.service.impl;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.redis.Cache;
import com.jfinal.plugin.redis.Redis;
import pkg.bean.ChartBean;
import pkg.bean.Result;
import pkg.model.Dish;
import pkg.model.DishRate;
import pkg.model.UserRate;
import pkg.service.AnalysisService;
import pkg.util.ChartUtil;
import pkg.util.UserHolder;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public class AnalysisServiceImpl implements AnalysisService {
    @Override
    public Result getDishChartByDate(Date d1, Date d2){
        List<Record> data = Db.template("dishAnalysisByDate", d1, d2).findByCache("dish_date","shared");
        ChartBean res = ChartUtil.singleChartData("菜品", "热度", data);
        return Result.ok(res);
    }
    @Override
    public Result getDishChartById(Long id, Date start, Date end) {
        List<Record> data = Db.template("dishAnalysisById",id,start,end).findByCache("dish_id",id);
        ChartBean res = ChartUtil.singleChartData("日期", "热度", data);
        return Result.ok(res);
    }
    @Override
    public Result getUserChartById(Long id){
        List<Record> data = Db.template("userAnalysisById",id).findByCache("user_id",id);
        ChartBean res = ChartUtil.singleChartData("菜品", "热度", data);
        return Result.ok(res);
    }
    @Override
    public Result getUserChartByDate(Long id, Date start, Date end) {
        List<Record> data = Db.template("userAnalysisByDate",id,start,end).findByCache("user_date",id);
        ChartBean res = ChartUtil.singleChartData("日期", "热度", data);
        return Result.ok(res);
    }
    @Override
    public Result getDishChartByRedis(){
        Cache redis = Redis.use("auth");
        Set<String> keys = redis.keys("jfinal:dish_count:*");
        List<Record> ls = new ArrayList<>();
        for(String i:keys) {
            long id = Long.parseLong(i.replaceAll("jfinal:dish_count:",""));
            String name = getDishNameByCache(redis, id);
            Record record = new Record();
            record.set("id",id);
            record.set("name",name);
            record.set("value",redis.get(i));
            ls.add(record);
        }
        ChartBean chartBean = ChartUtil.singleChartData("菜品", "热度", ls);
        return Result.ok(chartBean);

    }

    @Override
    public Result publishNewTest(Date date) {
        Cache redis = Redis.use("auth");
        redis.set("jfinal:admin:timestamp",date);
        resetPost();
        return Result.ok();
    }
    @Override
    public Result getHistoryById() {
        List<UserRate> ls = UserRate.dao.template("selectUrByUserId", UserHolder.getUser().getId())
                .findByCache("history",UserHolder.getUser().getId());
        Cache redis = Redis.use("auth");
        Map<Date,String> mp = new HashMap<>();
        for (UserRate userRate : ls) {
            Date date = userRate.getTime();
            Long id = userRate.getDishId();
            String name = getDishNameByCache(redis,id);
            // 如果map中已经存在该时间，将该id添加到对应的值后面
            if (mp.containsKey(date)) {
                String str = mp.get(date);
                mp.put(date, str + ", " + name);
            } else {
                mp.put(date, name);
            }
        }

        List<Record> res = new ArrayList<>();
        mp.forEach((date, s) -> {
            Record record = new Record();
            record.set("time",date);
            record.set("dishes",s);
            res.add(record);
        });
        res.sort((o1, o2) -> {
            Date d1 = o1.get("time");
            Date d2 = o2.get("time");
            return -d1.compareTo(d2);
        });
        return Result.ok(res);
    }
    @Override
    public Result postForm(Long id, List<Long> ls) {
        Cache redis = Redis.use("auth");
        for(long i:ls){
            UserRate ur = new UserRate();
            ur.setUserId(id);
            ur.setDishId(i);
            ur.setTime(getTimeStamp());
            ur.save();
            Integer n = redis.get("jfinal:dish_count:"+i);
            if(n==null) n=0;
            redis.set("jfinal:dish_count:"+i,n+1);
        }
        redis.set("jfinal:user_posted:"+id,true);
        return Result.ok();
    }
    @Override
    public Result isPosted(Long id) {
        Boolean res = Redis.use("auth").get("jfinal:user_posted:" + id);
        return Result.ok(!(res==null||!res));
    }

    private String getDishNameByCache(Cache redis, long id) {
        String name= redis.get("jfinal:dish_name:"+ id);
        if(name==null) {
            name = Dish.dao.findById(id).getName();
            redis.set("jfinal:dish_name:" + id, name);
        }
        return name;
    }

    private Date getTimeStamp(){
        Cache redis = Redis.use("auth");
        return redis.get("jfinal:admin:timestamp");
    }

    private void resetPost(){
        Cache redis = Redis.use("auth");
        Set<String> keys = redis.keys("jfinal:dish_count:*");
        for(String i:keys) {
            long dishId = Long.parseLong(i.replaceAll("jfinal:dish_count:",""));
            int demand = redis.get(i);
            DishRate dr = new DishRate();
            dr.setDemand(demand);
            dr.setDishId(dishId);
            dr.setTime(getTimeStamp());
            dr.save();
            redis.set(i,0);
        }
        keys = redis.keys("jfinal:user_posted:*");
        for(String i:keys) {
            redis.set(i,false);
        }
    }
}
