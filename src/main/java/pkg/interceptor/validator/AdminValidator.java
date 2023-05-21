package pkg.interceptor.validator;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;
import pkg.bean.Result;
import pkg.model.User;
import pkg.util.UserHolder;

public class AdminValidator extends Validator {
    @Override
    protected void validate(Controller c) {
        User user = UserHolder.getUser();
        if(user==null || !user.getIsManager()) addError("notAdmin","非管理员用户！");
    }

    @Override
    protected void handleError(Controller c) {
        c.renderJson(Result.err(c.getAttrForStr("notAdmin")));
    }
}
