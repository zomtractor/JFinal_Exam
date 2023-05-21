package pkg.service;

import pkg.bean.Pagination;
import pkg.bean.Result;
import pkg.model.Dish;

import java.util.List;

public interface DishService {
    Result add(Dish dish);

    Result getAll();

    Result getById(long id);

    Result delete(long id);

    Result update(Dish dish);

    Result getCount(Pagination pagination);

    Result getPaginate(Pagination pagination);

    Result getByName(String name);

    Result setEnable(List<Long> ids);

    Result getEnabled();
}
