package pkg.interceptor.validator;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;
import pkg.bean.Result;
import pkg.model.User;
import pkg.util.UserHolder;

public class PrivateValidator extends Validator {

    @Override
    protected void validate(Controller c) {
        User user = UserHolder.getUser();
        if(user.getIsManager()) return;
        validateRequired("id","privateErr","id not input");
        validateLong("id",user.getId(),user.getId(),"privateErr","no privilege");
    }

    @Override
    protected void handleError(Controller c) {
        c.renderJson(Result.err(c.getAttrForStr("privateErr")));
    }
}
