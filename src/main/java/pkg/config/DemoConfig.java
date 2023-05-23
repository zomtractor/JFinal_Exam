package pkg.config;

import com.jfinal.config.*;
import com.jfinal.json.FastJsonFactory;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.redis.RedisPlugin;
import com.jfinal.template.Engine;
import pkg.interceptor.CorsInterceptor;
import pkg.interceptor.ExceptionInterceptor;
import pkg.interceptor.TimeStampInterceptor;
import pkg.router.AdminRouter;
import pkg.router.FrontRouter;

public class DemoConfig extends JFinalConfig {

    public void configConstant(Constants me) {
        //me.setDevMode(true);
        me.setResolveJsonRequest(true);
        me.setInjectDependency(true);
        me.setJsonFactory(new FastJsonFactory());
        me.setInjectSuperClass(true);
    }

    public void configRoute(Routes me) {
        me.add(new AdminRouter());
        me.add(new FrontRouter());
        me.scan("pkg.controller.api.");
    }

    public void configEngine(Engine me) {
        me.setDevMode(true);
        me.addSharedStaticMethod(StrKit.class);
        me.addSharedFunction("static/template/enjoy.html");
    }
    public void configPlugin(Plugins me) {

        RedisPlugin rp = RedisConfig.getRp();
        me.add(rp);
        DruidPlugin dp = DbConfig.getDruid();
        me.add(dp);
        ActiveRecordPlugin arp = DbConfig.getArp();
        me.add(arp);
    }
    public void configInterceptor(Interceptors me) {
        me.addGlobalActionInterceptor(new ExceptionInterceptor());
        me.addGlobalActionInterceptor(new CorsInterceptor());
        me.addGlobalActionInterceptor(new TimeStampInterceptor());
    }
    public void configHandler(Handlers me) {
        //me.add(new StaticHandler());
    }
}