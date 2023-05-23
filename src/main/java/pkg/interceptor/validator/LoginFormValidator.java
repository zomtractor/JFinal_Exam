package pkg.interceptor.validator;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;
import pkg.bean.Result;

public class LoginFormValidator extends Validator {
    @Override
    protected void validate(Controller c) {
        // check null
        validateRequired("username","loginErrKey","用户名不能为空");
        validateRequired("password","loginErrKey","密码不能为空");
        validateRequired("checkcode","loginErrKey","验证码不能为空");

        validateString("username",0,64,"loginErrKey","用户名不能超过64位");
        validateString("password",12,64,"loginErrKey","密码只能在12-64位之间");
        validateRegex("password","^(?=.*[a-z])(?=.*[1-9]).+$","loginErrKey","密码必须包含小写字母和数字！");
        //validateString("checkcode",4,4,"loginErrKey","验证码为4位！");
    }

    @Override
    protected void handleError(Controller c) {
        c.renderJson(Result.err(c.getAttrForStr("loginErrKey")));
    }
}
