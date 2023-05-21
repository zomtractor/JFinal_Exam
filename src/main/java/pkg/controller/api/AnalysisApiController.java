package pkg.controller.api;

import com.jfinal.aop.Inject;
import com.jfinal.core.Controller;
import com.jfinal.core.Path;
import pkg.service.impl.AnalysisServiceImpl;

import java.util.Date;

@Path("/admin/data/api")
public class AnalysisApiController extends Controller {
    @Inject
    private AnalysisServiceImpl analysisService;

    /**
     * 查询日期范围内的饼图
     */
    public void getDishChartByDate(){
        Date start = getAttr("start");
        Date end = getAttr("end");
        renderJson(analysisService.getDishChartByDate(start,end));
    }

    /**
     * 获取日期范围内某菜品的热度图
     */
    public void getDishChartById(){
        Long id = getLong("id");
        Date start = getAttr("start");
        Date end = getAttr("end");
        renderJson(analysisService.getDishChartById(id,start,end));
    }

    /**
     * 获取日期范围内某用户的喜好图
     */
    public void getUserChartByDate(){
        Long id = getLong("id");
        Date start = getAttr("start");
        Date end = getAttr("end");
        renderJson(analysisService.getUserChartByDate(id,start,end));
    }

    /**
     * 获取某用户的喜好饼图
     */
    public void getUserChartById(){
        Long id = getLong();
        renderJson(analysisService.getUserChartById(id));
    }

    /**
     * 获取当前正在投票中的菜品热度（从redis）
     */
    public void getDishChartByRedis(){
        renderJson(analysisService.getDishChartByRedis());
    }

    /**
     * 管理员发布新投票
     */
    public void publishNewTask(){
        Date date = getAttr("timestamp");
        System.out.println(date);
        renderJson(analysisService.publishNewTest(date));
    }


}
