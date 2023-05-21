package pkg.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import pkg.bean.Result;
import pkg.util.UserHolder;

public class ExceptionInterceptor implements Interceptor {
    @Override
    public void intercept(Invocation inv) {
        try{
            inv.invoke();
        } catch (Exception e){
            e.printStackTrace();
            UserHolder.removeUser();
            inv.getController().renderJson(Result.err(e.getMessage()));
        }
    }
}
