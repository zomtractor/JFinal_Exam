package pkg.controller.api;

import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.aop.Inject;
import com.jfinal.core.Controller;
import com.jfinal.core.Path;
import com.jfinal.core.paragetter.Para;
import pkg.bean.Pagination;
import pkg.interceptor.TokenInterceptor;
import pkg.interceptor.validator.AdminValidator;
import pkg.interceptor.validator.DishFormValidator;
import pkg.model.Dish;
import pkg.service.impl.DishServiceImpl;

import java.util.List;

@Path("/dish/api")
@Before({TokenInterceptor.class,AdminValidator.class})
public class DishApiController extends Controller implements ApiController {
    @Inject
    private DishServiceImpl dishService;

    /**
     * 根据id查询
     */
    public void getDish(){
        long id = getLong();
        renderJson(dishService.getById(id));
    }

    /**
     * 根据菜品查询
     */
    public void getDishByName(){
        String name = get("name");
        renderJson(dishService.getByName(name));
    }

    /**
     * 更新
     * @param dish
     */
    @Before(DishFormValidator.class)
    public void updateDish(Dish dish){
        System.out.println(dish);
        renderJson(dishService.update(dish));
    }
    public void setEnable(@Para List<Long> ls){
        renderJson(dishService.setEnable(ls));
    }

    /**
     * 添加
     * @param dish
     */
    @Before(DishFormValidator.class)
    public void addDish(Dish dish){
        System.out.println(dish);
        renderJson(dishService.add(dish));
    }

    /**
     * 删除
     */
    public void deleteDish(){
        long id = getLong();
        renderJson(dishService.delete(id));
    }

    /**
     * 分页查询数量
     * @param pageBean 分页信息
     */
    public void getDishCount(Pagination pageBean){
        renderJson(dishService.getCount(pageBean));
    }

    /**
     * 分页查询
     * @param pageBean 分页信息
     */
    public void getDishPagination(Pagination pageBean){
        renderJson(dishService.getPaginate(pageBean));
    }

    /**
     * 查询admin提供的菜品
     */
    @Clear(AdminValidator.class)
    public void getEnabled(){
        renderJson(dishService.getEnabled());
    }
}
