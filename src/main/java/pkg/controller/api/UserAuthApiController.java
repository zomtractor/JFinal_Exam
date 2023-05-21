package pkg.controller.api;

import com.jfinal.aop.Before;
import com.jfinal.aop.Inject;
import com.jfinal.core.ActionKey;
import com.jfinal.core.Controller;
import com.jfinal.core.Path;
import com.jfinal.plugin.redis.Cache;
import com.jfinal.plugin.redis.Redis;
import pkg.bean.LoginForm;
import pkg.bean.Result;
import pkg.interceptor.TokenInterceptor;
import pkg.interceptor.validator.LoginFormValidator;
import pkg.service.UserAuthService;
import pkg.service.impl.UserAuthServiceImpl;
import pkg.util.UserHolder;

@Path("auth/api")
public class UserAuthApiController extends Controller implements ApiController{

    @Inject(UserAuthServiceImpl.class)
    private UserAuthService userAuthService;

    /**
     * 登录接口
     * @param loginForm 前端登录表单
     */
    @Before(LoginFormValidator.class)
    public void login(LoginForm loginForm){
        System.out.println(loginForm);
        Result checkResult = userAuthService.verifyCheckCode(getRequest().getSession().getId(),loginForm.getCheckcode());
        if(checkResult.isOk()){
            renderJson(userAuthService.login(loginForm.getUsername(),loginForm.getPassword()));
        } else renderJson(checkResult);
    }

    /**
     * 注册接口
     * @param loginForm 前端注册表单
     */
    @Before(LoginFormValidator.class)
    public void register(LoginForm loginForm){
        System.out.println(loginForm);
        Result checkResult = userAuthService.verifyCheckCode(getRequest().getSession().getId(),loginForm.getCheckcode());
        if(checkResult.isOk()){
            renderJson(userAuthService.register(loginForm.getUsername(),loginForm.getPassword()));
        } else renderJson(checkResult);
    }

    /**
     * 生成验证码串和图片
     * @throws Exception IO流异常
     */
    @ActionKey("/auth/api/generate_checkcode")
    public void generateCheckCode() throws Exception{
        String id = getRequest().getSession().getId();
        Result res =  userAuthService.generateCheckCode(id,getResponse().getOutputStream());
        if(res.isOk()) renderNull();
        else renderJson(res);
    }

    /**
     * 校验验证码
     */
    @ActionKey("/auth/api/checkcode_verify")
    public void verifyCheckCode(){
        String id = getRequest().getSession().getId();
        Result res = userAuthService.verifyCheckCode(id, getPara("code"));
        renderJson(res);
    }

    /**
     * 校验token登陆状态，由拦截器实现
     */
    @Before(TokenInterceptor.class)
    @ActionKey("/auth/api/check_token")
    public void checkToken(){
        renderJson(Result.ok());
    }

    /**
     * 获取user信息，不含敏感信息
     */
    public void getFilteredUser(){
        renderJson(UserHolder.getUser().set("password",""));
    }


    /**
     * 生成二维码
     */
    @ActionKey("/auth/api/summon_qrcode")
    public void summonQrCode(){
        String key = get("key");
        Cache auth = Redis.use("auth");
        renderQrCode("http://192.168.43.199:8080/auth/api/hit_qrcode?key="+key,500,500);
    }

    /**
     * 客户端扫描二维码
     */
    @ActionKey("/auth/api/hit_qrcode")
    public void hitQrCode(){
        String token = getHeader("authorization");
        System.out.println(token);
        String key = get("key");
        Cache auth = Redis.use("auth");
        auth.setex("jfinal:auth:qrcode:"+key,300,token);
        renderJson("uploaded. your send: "+token);
    }

    /**
     * 检验二维码是否被扫
     */
    @ActionKey("/auth/api/check_qrcode")
    public void checkQrCode(){
        String key = get("key");
        Cache auth = Redis.use("auth");
        String token = auth.get("jfinal:auth:qrcode:"+key);
        if(token==null) renderJson(Result.err(null));
        else renderJson(Result.ok(token));
    }

    /**
     * 用户退出登录
     */
    public void logOut(){
        renderJson(userAuthService.logOut(getHeader("authorization")));
    }


}
