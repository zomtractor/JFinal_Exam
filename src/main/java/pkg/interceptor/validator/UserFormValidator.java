package pkg.interceptor.validator;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;
import pkg.bean.Result;
import pkg.model.User;

public class UserFormValidator extends Validator {
    @Override
    protected void validate(Controller c) {
        validateRequired("username","userErr","用户名不为空！");
        validateRequired("password","userErr","密码不为空！");
        validateString("password",12,64,"userErr","密码只能在12-64位之间");
        validateRegex("password","^(?=.*[a-z])(?=.*[1-9]).+$","userErr","密码必须包含小写字母和数字！");
        String ageStr = c.get("age");
        if(ageStr!=null){
            try{
                Integer.valueOf(ageStr);
            } catch (Exception e){
                addError("userErr","年龄必须为整数");
            }
        }

    }
    @Override
    protected void handleError(Controller c) {
        c.renderJson(Result.err(c.getAttrForStr("userErr")));
    }
}
