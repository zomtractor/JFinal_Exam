package pkg.service.impl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import pkg.bean.Pagination;
import pkg.bean.Result;
import pkg.config.DbConfig;
import pkg.config.RedisConfig;
import pkg.model.User;
import pkg.service.UserService;
import pkg.util.UserHolder;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserServiceTest  {
    private UserService service;
    @Before
    public void a_init(){
        DbConfig.getDruid().start();
        DbConfig.getArp().start();
        RedisConfig.getRp().start();
        service = new UserServiceImpl();
        User user = new User().set("id",1).set("is_manager",true).set("username","admin");
        UserHolder.saveUser(user);
    }

    @Test
    public void b_add() {
        User user = new User();
        user.setId(55555L);
        user.setUsername("testabcd"+System.currentTimeMillis()+"");
        user.setPassword("abcd12345678");
        try{
            Result res = service.add(user);
            Assert.assertTrue(res.isOk());
        } catch (Exception e){
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    public void c_getById() {
        long id = 55555;
        Result res = service.getById(id);
        Assert.assertTrue(res.isOk());
    }

    @Test
    public void d_getAll() {
        Result res = service.getAll();
        Assert.assertTrue(res.isOk());
    }

    @Test
    public void e_getCount() {
        Pagination pagination = new Pagination();
        pagination.setCurrentPage(1);
        pagination.setPageSize(5);
        pagination.setData(new User());
        Result res = service.getCount(pagination);
        Assert.assertTrue(res.isOk());
    }

    @Test
    public void f_update() {
        User user = User.dao.findById(55555);
        user.setIsManager(true);
        Result res = service.update(user);
        Assert.assertTrue(res.isOk());
    }

    @Test
    public void g_getPaginate() {
        Pagination pagination = new Pagination();
        pagination.setCurrentPage(1);
        pagination.setPageSize(5);
        pagination.setData(new User());
        Result res = service.getPaginate(pagination);
        Assert.assertTrue(res.isOk());
    }



    @Test
    public void h_getByName() {
        Result res = service.getByName("testabcd");
        Assert.assertTrue(res.isOk());
    }

    @Test
    public void i_delete() {
        Result res = service.delete(55555);
        Assert.assertTrue(res.isOk());
    }
}
