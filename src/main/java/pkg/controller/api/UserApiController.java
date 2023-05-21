package pkg.controller.api;

import com.jfinal.aop.Before;
import com.jfinal.aop.Inject;
import com.jfinal.core.Controller;
import com.jfinal.core.Path;
import pkg.bean.Pagination;
import pkg.bean.Result;
import pkg.interceptor.TokenInterceptor;
import pkg.interceptor.validator.AdminValidator;
import pkg.interceptor.validator.PrivateValidator;
import pkg.interceptor.validator.UserFormValidator;
import pkg.model.User;
import pkg.service.impl.UserServiceImpl;
import pkg.util.UserHolder;

/**
  用户CRUD接口
 */

@Path("/user/api")
@Before(TokenInterceptor.class)
public class UserApiController extends Controller {

    @Inject
    private UserServiceImpl userService;

    /**
     * 根据id查询用户
     */
    @Before(PrivateValidator.class)
    public void getUser(){
        long id = getLong();
        renderJson(userService.getById(id));
    }

    /**
     * 更新用户
     * @param user
     */
    @Before({PrivateValidator.class,UserFormValidator.class})
    public void updateUser(User user){
        System.out.println(user);
        renderJson(userService.update(user));
    }

    /**
     * 添加新用户（admin）
     * @param user
     */
    @Before({AdminValidator.class, UserFormValidator.class})
    public void addUser(User user){
        System.out.println(user);
        renderJson(userService.add(user));
    }

    /**
     * 删除用户
     */
    @Before(AdminValidator.class)
    public void deleteUser(){
        long id = getLong();
        renderJson(userService.delete(id));
    }

    /**
     * 根据用户名查询用户
     */
    @Before(AdminValidator.class)
    public void getUserByName(){
        String name = get("name");
        renderJson(userService.getByName(name));
    }

    /**
     * 获取当前用户
     */
    public void getUserByHolder(){
        renderJson(Result.ok(UserHolder.getUser()));
    }

    /**
     * 首次分页查询用户数量
     * @param pageBean
     */
    @Before(AdminValidator.class)
    public void getUserCount(Pagination pageBean){
        renderJson(userService.getCount(pageBean));
    }

    /**
     * 分页查询用户
     * @param pageBean
     */
    @Before(AdminValidator.class)
    public void getUserPagination(Pagination pageBean){
        renderJson(userService.getPaginate(pageBean));
    }
}
