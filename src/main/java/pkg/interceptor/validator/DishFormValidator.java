package pkg.interceptor.validator;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;
import pkg.bean.Result;
import pkg.model.Dish;

public class DishFormValidator extends Validator {
    @Override
    protected void validate(Controller c) {
        setShortCircuit(true);
        validateRequired("name","dishErr","菜品不为空！");
        if(c.get("price")==null){
            addError("dishErr","价格必须为整数或小数！");
        }
    }

    @Override
    protected void handleError(Controller c) {
        c.renderJson(Result.err(c.getAttrForStr("dishErr")));
    }
}
