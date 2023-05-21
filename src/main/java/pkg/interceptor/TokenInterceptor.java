package pkg.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import pkg.bean.Result;
import pkg.controller.api.ApiController;
import pkg.model.User;
import pkg.util.TokenUtil;
import pkg.util.UserHolder;

/**
 * 拦截无token请求，并给当前线程注入User对象
 */
public class TokenInterceptor implements Interceptor {
    @Override
    public void intercept(Invocation inv) {
        Controller controller = inv.getController();
        User user = TokenUtil.verifyToken(controller.getHeader("authorization"));
        if(user!=null){
            UserHolder.saveUser(user);
            inv.invoke();
            UserHolder.removeUser();
        } else {
            if(controller instanceof ApiController)
                controller.renderJson(Result.err("token invalid!"));
            else
                controller.redirect("/auth/login.html");
        }
    }
}
