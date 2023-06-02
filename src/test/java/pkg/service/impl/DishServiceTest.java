package pkg.service.impl;

import org.junit.*;
import org.junit.runners.MethodSorters;
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

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DishServiceTest {

    private DishService service;
    @Before
    public void a_init(){
        DbConfig.getDruid().start();
        DbConfig.getArp().start();
        RedisConfig.getRp().start();
        service = new DishServiceImpl();
        User user = new User().set("id",1).set("is_manager",true).set("username","admin");
        UserHolder.saveUser(user);
    }

    @Test
    public void b_add() {
        Dish dish = new Dish();
        dish.setName("testDish");dish.setPrice(BigDecimal.valueOf(2));dish.setDescription("test description");
        dish.setPicture("http://localhost:8081/jfinal/dish/1683362243091.jpg");dish.setEnable(true);
        Result res = service.add(dish);
        Assert.assertTrue(res.isOk());
        try{
            service.add(dish);
            Assert.fail();  //不能重复添加
        } catch (Exception e){
            System.out.println("expected");
        }
    }

    @Test
    public void c_getAll() {
        Result res = service.getAll();
        Assert.assertTrue(res.isOk());
    }

    @Test
    public void d_getById() {
        long id = 3;
        Result res = service.getById(id);
        Assert.assertTrue(res.isOk());
        id = -4;    //不存在
        res = service.getById(id);
        Assert.assertFalse(res.isOk());
    }


    @Test
    public void e_update() {
        Result testDish = service.getByName("testDish");
        List<Dish> data = (List<Dish>) testDish.getData();
        Dish dish = data.get(0);
        dish.setName("tttt");
        dish.setPrice(BigDecimal.valueOf(2));
        dish.setDescription("test description");
        dish.setPicture("http://localhost:8081/jfinal/dish/1683362243091.jpg");
        dish.setEnable(true);
        Result res = service.update(dish);
        Assert.assertTrue(res.isOk());

    }

    @Test
    public void f_getCount() {
        Pagination pagination = new Pagination();
        Dish dish = new Dish();
        dish.setName("t");
        pagination.setData(dish);
        pagination.setCurrentPage(1);
        pagination.setPageSize(5);
        Result res = service.getCount(pagination);
        Assert.assertTrue(res.isOk());

        dish.setName("NONONONONONO");
        pagination = new Pagination();
        pagination.setData(dish);
        res = service.getCount(pagination);
        System.out.println(res);
    }

    @Test
    public void g_getPaginate() {
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
    public void h_delete() {
        Dish dish = Dish.dao.findFirst("select * from dish where name = 'tttt'");
        Result res = service.delete(dish.getId());
        Assert.assertTrue(res.isOk());
    }

    @Test
    public void i_getByName() {
        String name = "西红柿";
        Result res = service.getByName(name);
        Assert.assertTrue(res.isOk());
    }

    @Test
    public void j_setEnable() {
        List<Long> ids = Arrays.asList(3L,4L,5L,6L,7L);
        Result res = service.setEnable(ids);
        Assert.assertTrue(res.isOk());
    }

    @Test
    public void k_getEnabled() {
        Result res = service.getEnabled();
        Assert.assertTrue(res.isOk());
    }


    @After
    public void l_destroy(){
        UserHolder.removeUser();
    }
}
