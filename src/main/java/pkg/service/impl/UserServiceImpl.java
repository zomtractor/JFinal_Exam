package pkg.service.impl;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import lombok.SneakyThrows;
import pkg.bean.Pagination;
import pkg.bean.Result;
import pkg.model.User;
import pkg.service.UserService;
import pkg.util.TokenUtil;
import pkg.util.UserHolder;

import java.util.List;

public class UserServiceImpl implements UserService {

    @Override
    public Result getById(long id){
        return Result.status(User.dao.findById(id));
    }

    @Override
    public Result getAll(){
        return Result.ok(User.dao.findAll());
    }

    @Override
    public Result getCount(Pagination pagination){
        Integer res = Db.template("selectUserCount", Kv.of("user",pagination.getData())) .queryInt();
        if(res!=null){
            //pagination.setCurrentPage(1);
            pagination.setTotalRows(res);
            return Result.ok(pagination);
        } else {
            return Result.err("error");
        }
    }

    @Override
    public Result update(User user){
        user.update();
        String token = TokenUtil.generateToken(user);
        UserHolder.removeUser();
        UserHolder.saveUser(user);
        return Result.ok(token);
    }

    @Override
    public Result add(User user){
        try{
            User u1 = User.dao.template("selectUserByUserName",user.getUsername()).findFirst();
            if(u1!=null) return Result.err("用户已存在");
        } catch (Exception e){
            return Result.err("用户已存在");
        }
        return Result.status(user.save());
    }

    @Override
    public Result getPaginate(Pagination pagination){
        List<User> users = User.dao.template("selectUserByCondition", Kv.of("user",pagination.getData()))
                .paginate(pagination.getCurrentPage(), pagination.getPageSize())
                .getList();
        return Result.ok(users);
    }

    @Override
    public Result delete(long id) {
        return Result.status(User.dao.deleteById(id));
    }

    @Override
    public Result getByName(String name){
        List<User> list = User.dao.template("selectUserByName", name).find();
        return Result.ok(list);
    }
}
