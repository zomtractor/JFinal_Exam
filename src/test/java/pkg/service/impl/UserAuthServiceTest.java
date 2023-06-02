package pkg.service.impl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pkg.bean.Result;
import pkg.config.DbConfig;
import pkg.config.RedisConfig;
import pkg.model.User;
import pkg.service.UserAuthService;
import pkg.util.UserHolder;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class UserAuthServiceTest {
    private UserAuthService service;

    @Before
    public void init(){
        service = new UserAuthServiceImpl();
        DbConfig.getDruid().start();
        DbConfig.getArp().start();
        RedisConfig.getRp().start();
        User user = new User().set("id",1).set("is_manager",true).set("username","admin");
        UserHolder.saveUser(user);
    }

    @Test
    public void generateCheckCode() {
        String sessionId = "123456";
        OutputStream os = new ByteArrayOutputStream();
        Result res = service.generateCheckCode(sessionId, os);
        Assert.assertTrue(res.isOk());
    }
    @Test
    public void verifyCheckCode() {
        String sessionId = "123456";
        String code = "1234";
        Result res = service.verifyCheckCode(sessionId, code);
        Assert.assertTrue(res.isOk());
    }



    @Test
    public void login() {
        String username = "lhc";
        String password = "abcd12345678";
        Result res = service.login(username, password);
        Assert.assertTrue(res.isOk());

        username = "lhcccc";
        res = service.login(username, password);
        Assert.assertFalse(res.isOk());
    }

    @Test
    public void register() {
        String username = "lhc";
        String password = "abcd12345678";
        Result res = service.register(username, password);
        Assert.assertFalse(res.isOk());
        username = ""+System.currentTimeMillis();

        res = service.register(username, password);
        Assert.assertTrue(res.isOk());
    }

    @Test
    public void logOut() {
        String token = System.currentTimeMillis()+"";
        Result res = service.logOut(token);
        Assert.assertTrue(res.isOk());
    }
}
