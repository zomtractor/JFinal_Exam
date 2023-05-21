package pkg.service.impl;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import pkg.bean.Pagination;
import pkg.bean.Result;
import pkg.model.Dish;
import pkg.service.DishService;

import java.util.List;

public class DishServiceImpl implements DishService {
    @Override
    public Result add(Dish dish){
        return Result.status(dish.save());
    }
    @Override
    public Result getAll(){
        return Result.ok(Dish.dao.findAll());
    }
    @Override
    public Result getById(long id){
        return Result.status(Dish.dao.findById(id));
    }

    @Override
    public Result delete(long id){
        return Result.status(Dish.dao.deleteById(id));
    }

    @Override
    public Result update(Dish dish){
        return Result.status(dish.update());
    }

    @Override
    public Result getCount(Pagination pagination){
        Integer res = Db.template("selectDishCount", Kv.of("dish",pagination.getData())) .queryInt();
        if(res!=null){
            //pagination.setCurrentPage(1);
            pagination.setTotalRows(res);
            return Result.ok(pagination);
        } else {
            return Result.err("error");
        }
    }

    @Override
    public Result getPaginate(Pagination pagination){
        List<Dish> dishes = Dish.dao.template("selectDishByCondition", Kv.of("dish",pagination.getData()))
                .paginate(pagination.getCurrentPage(), pagination.getPageSize())
                .getList();
        return Result.ok(dishes);
    }

    @Override
    public Result getByName(String name) {
        List<Dish> dishes = Dish.dao.template("selectDishByName",name).findByCache("dish_name","share");
        return Result.ok(dishes);
    }

    @Override
    public Result setEnable(List<Long> ids) {
        int res1 = Db.template("setDishUnable").update();
        int res2 = Db.template("setDishEnable",ids).update();
        return Result.status(res1+res2>=0);
    }

    @Override
    public Result getEnabled() {
        List<Dish> dishes = Dish.dao.template("selectEnabled").findByCache("dish_enabled","share");
        return Result.ok(dishes);
    }
}
