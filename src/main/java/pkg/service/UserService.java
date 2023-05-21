package pkg.service;

import pkg.bean.Pagination;
import pkg.bean.Result;
import pkg.model.User;

public interface UserService {
    Result getById(long id);

    Result getAll();

    Result getCount(Pagination pagination);

    Result update(User user);

    Result add(User user);

    Result getPaginate(Pagination pagination);

    Result delete(long id);

    Result getByName(String name);
}
