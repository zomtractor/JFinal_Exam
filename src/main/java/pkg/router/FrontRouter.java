package pkg.router;

import com.jfinal.config.Routes;
import pkg.controller.MenuController;
import pkg.controller.UserAuthController;

public class FrontRouter extends Routes {
    @Override
    public void config() {
        add("/auth", UserAuthController.class,"/auth");
        add("/menu", MenuController.class,"/menu");
    }
}
