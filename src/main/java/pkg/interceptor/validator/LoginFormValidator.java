package pkg.interceptor.validator;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;
import pkg.bean.Result;

public class LoginFormValidator extends Validator {
    @Override
    protected void validate(Controller c) {
        // check null
        validateRequired("username","loginErrKey1","用户名不能为空");
        validateRequired("password","loginErrKey2","密码不能为空");
        validateRequired("checkcode","loginErrKey3","验证码不能为空");

        validateString("username",0,64,"loginErrKey4","用户名不能超过64位");
        validateString("password",12,64,"loginErrKey5","密码只能在12-64位之间");
        validateRegex("password","^(?=.*[a-z])(?=.*[1-9]).+$","loginErrKey6","密码必须包含小写字母和数字");
        validateString("checkcode",4,4,"loginErrKey7","验证码为4位");
    }

    @Override
    protected void handleError(Controller c) {
        for(int i=1;i<=7;i++){
            String key = "loginErrKey"+i;
            String res = c.getAttrForStr(key);
            if(res!=null){
                c.renderJson(Result.err(res));
                break;
            }
        }

    }
}
