package pkg.service.impl;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pkg.bean.Pagination;
import pkg.bean.Result;
import pkg.config.DbConfig;
import pkg.config.RedisConfig;
import pkg.model.Dish;
import pkg.model.User;
import pkg.service.DishService;
import pkg.util.UserHolder;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

public class DishServiceTest {

    private DishService service;
    @Before
    public void init(){
        DbConfig.getDruid().start();
        DbConfig.getArp().start();
        RedisConfig.getRp().start();
        service = new DishServiceImpl();
        User user = new User().set("id",1).set("is_manager",true).set("username","admin");
        UserHolder.saveUser(user);
    }


    @Test
    public void add() {
        Dish dish = new Dish();
        dish.setName("testDish");
        dish.setPrice(BigDecimal.valueOf(2));
        dish.setDescription("test description");
        dish.setPicture("http://localhost:8081/jfinal/dish/1683362243091.jpg");
        dish.setEnable(true);
        Result res = service.add(dish);
        Assert.assertTrue(res.isOk());
    }

    @Test
    public void getAll() {
        Result res = service.getAll();
        Assert.assertTrue(res.isOk());
    }

    @Test
    public void getById() {
        long id = 3;
        Result res = service.getById(id);
        Assert.assertTrue(res.isOk());
    }


    @Test
    public void update() {
        Dish dish = new Dish();
        dish.setId(11L);
        dish.setName("tttt");
        dish.setPrice(BigDecimal.valueOf(2));
        dish.setDescription("test description");
        dish.setPicture("http://localhost:8081/jfinal/dish/1683362243091.jpg");
        dish.setEnable(true);
    }

    @Test
    public void getCount() {
        Pagination pagination = new Pagination();
        Dish dish = new Dish();
        dish.setName("t");
        pagination.setData(dish);
        pagination.setCurrentPage(1);
        pagination.setPageSize(5);
        Result res = service.getCount(pagination);
        Assert.assertTrue(res.isOk());
    }

    @Test
    public void getPaginate() {
        Pagination pagination = new Pagination();
        Dish dish = new Dish();
        dish.setName("t");
        pagination.setData(dish);
        pagination.setCurrentPage(1);
        pagination.setPageSize(5);
        Result res = service.getPaginate(pagination);
        Assert.assertTrue(res.isOk());
    }

    @Test
    public void delete() {
        long id = 11;
        Result res = service.delete(id);
        Assert.assertTrue(res.isOk());
    }

    @Test
    public void getByName() {
        String name = "西红柿";
        Result res = service.getByName(name);
        Assert.assertTrue(res.isOk());
    }

    @Test
    public void setEnable() {
        List<Long> ids = Arrays.asList(3L,4L,5L,6L,7L);
        Result res = service.setEnable(ids);
        Assert.assertTrue(res.isOk());
    }

    @Test
    public void getEnabled() {
        Result res = service.getEnabled();
        Assert.assertTrue(res.isOk());
    }


    @After
    public void destroy(){
        UserHolder.removeUser();
    }
}
