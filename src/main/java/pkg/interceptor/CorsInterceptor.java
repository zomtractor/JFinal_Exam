package pkg.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 处理跨域问题、拦截嗅探请求
 */
public class CorsInterceptor implements Interceptor {
    @Override
    public void intercept(Invocation inv) {
        HttpServletRequest request = inv.getController().getRequest();
        HttpServletResponse response = inv.getController().getResponse();

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            inv.getController().renderNull();
            return;
        }
        inv.invoke();
    }
}
