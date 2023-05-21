package pkg.interceptor.validator;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;
import pkg.bean.Result;

public class DishFormValidator extends Validator {
    @Override
    protected void validate(Controller c) {
        validateRequired("name","dishErr","菜品不为空！");
    }

    @Override
    protected void handleError(Controller c) {
        c.renderJson(Result.err(c.getAttrForStr("dishErr")));
    }
}
