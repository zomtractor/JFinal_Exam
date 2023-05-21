package pkg.router;

import com.jfinal.config.Routes;
import pkg.controller.AdminController;

public class AdminRouter extends Routes {
    @Override
    public void config() {
        add("/admin",AdminController.class);
        //setBaseViewPath("/admin");
    }
}
