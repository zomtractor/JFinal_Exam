package pkg.service.impl;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.redis.Cache;
import com.jfinal.plugin.redis.Redis;
import jdk.nashorn.internal.parser.Token;
import pkg.bean.Result;
import pkg.model.User;
import pkg.service.UserAuthService;
import pkg.util.CheckCodeUtil;
import pkg.util.TokenUtil;
import pkg.util.UserHolder;

import java.io.IOException;
import java.io.OutputStream;

public class UserAuthServiceImpl implements UserAuthService {

    @Override
    public Result verifyCheckCode(String sessionId, String code) {
        return Result.ok("true");
//        Cache auth = Redis.use("auth");
//        String ans = auth.get(String.format("jfinal:auth:checkcode:%s",sessionId));
//        if(ans==null) return Result.err("checkcode expired.");
//        if(code.trim().equalsIgnoreCase(ans)){
//            return Result.ok("true");
//        } else {
//            return Result.err("invalid checkcode.");
//        }

    }

    @Override
    public Result generateCheckCode(String sessionId, OutputStream os) {

        try {
            String code = CheckCodeUtil.outputVerifyImage(100,40,os,4);
            Cache auth = Redis.use("auth");
            auth.setex(String.format("jfinal:auth:checkcode:%s",sessionId),300,code);
            return Result.ok(null);
        } catch (IOException e) {
            e.printStackTrace();
            return Result.err("exception.");
        }
    }

    @Override
    public Result login(String username, String password) {
        //User user = User.dao.findFirst("select * from user where username = ?", username);
        User user = User.dao.template("selectUserByUserName",username).findFirst();
        if(user==null) return Result.err("用户不存在！");
        else if(!user.getPassword().equals(password)) return Result.err("密码不正确！");
        else {
            String token = TokenUtil.generateToken(user);
            //UserHolder.saveUser(user);
            return Result.ok(token);
        }

    }

    @Override
    public Result register(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        User u1 = User.dao.template("selectUserByUserName",user.getUsername()).findFirst();
        if(u1!=null) return Result.err("用户已存在");
        return Result.status(user.save());
    }

    @Override
    public Result logOut(String token){
        if(!token.equals("admin"))
            TokenUtil.removeToken(token);
        return Result.ok();
    }

}
