package pkg.config;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import pkg.model.*;

import javax.sql.DataSource;

public class DbConfig {
    private static final String url = "jdbc:mysql://localhost/jfinal_exam";
    private static final String username = "root";
    private static final String password = "1234";
    private static DruidPlugin druid;
    private static ActiveRecordPlugin arp;

    public static DruidPlugin getDruid(){
        return druid==null? druid = new DruidPlugin(url,username,password): druid;
    }
    public static ActiveRecordPlugin getArp(){
        if(arp==null){
            arp = new ActiveRecordPlugin(getDruid());
            _MappingKit.mapping(arp);
            arp.getEngine().addSharedMethod(StrKit.class);
            arp.addSqlTemplate("pkg/model/user.sql");
            arp.addSqlTemplate("pkg/model/dish.sql");
            arp.addSqlTemplate("pkg/model/multiple.sql");
            arp.setCache(new RedisConfig());
            //arp.setShowSql(true);
        }
        return arp;
    }
}
