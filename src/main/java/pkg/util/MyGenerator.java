package pkg.util;

import com.jfinal.kit.PathKit;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.activerecord.generator.Generator;
import com.jfinal.plugin.druid.DruidPlugin;

import javax.sql.DataSource;

public class MyGenerator {

    public static DataSource getDataSource() {
        String jdbcUrlString = "jdbc:mysql://127.0.0.1:3306/jfinal_exam";// 数据库连接

        DruidPlugin druidPlugin = new DruidPlugin(jdbcUrlString, "root", "1234");  //数据库连接插件
        druidPlugin.start();
        return druidPlugin.getDataSource();
    }

    public static void main(String[] args) {
        // model 所使用的包名 (MappingKit 默认使用的包名)
        String modelPackageName = "pkg.model";

// base model 所使用的包名
        String baseModelPackageName = modelPackageName + ".base";

// base model 文件保存路径
// 注意从 jfinal 4.9.12 版开始，PathKit.getWebRootPath() 在此的用法要改成 System.getProperty("user.dir")
        String baseModelOutputDir = System.getProperty("user.dir") + "/src/main/java/" + baseModelPackageName.replace('.', '/');

// model 文件保存路径 (MappingKit 与 DataDictionary 文件默认保存路径)
        String modelOutputDir = baseModelOutputDir + "/..";

        System.out.println("输出路径：" + baseModelOutputDir);

// 创建生成器
        Generator gen = new Generator(getDataSource(), baseModelPackageName, baseModelOutputDir, modelPackageName, modelOutputDir);

// 设置数据库方言
        gen.setDialect(new MysqlDialect());

// 在 getter、setter 方法上生成字段备注内容
        gen.setGenerateRemarks(true);

// 开始生成代码
        gen.generate();
    }
}