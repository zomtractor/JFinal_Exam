package pkg;

import com.jfinal.server.undertow.UndertowServer;
import pkg.config.DemoConfig;

public class MainApplication {
    public static void main(String[] args) {
        UndertowServer.start(DemoConfig.class, 8080, false);
    }

}