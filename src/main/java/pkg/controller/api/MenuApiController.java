package pkg.controller.api;

import com.jfinal.aop.Before;
import com.jfinal.aop.Inject;
import com.jfinal.core.Controller;
import com.jfinal.core.Path;
import com.jfinal.core.paragetter.Para;
import pkg.interceptor.TokenInterceptor;
import pkg.service.impl.AnalysisServiceImpl;
import pkg.util.UserHolder;

import java.util.List;

@Before(TokenInterceptor.class)
@Path("/menu/api")
public class MenuApiController extends Controller implements ApiController {
    @Inject
    private AnalysisServiceImpl analysisService;

    /**
     * 历史页，用户历史记录
     */
    public void getHistoryById(){
        renderJson(analysisService.getHistoryById());
    }

    /**
     * 历史页，用户柄图数据
     */
    public void getUserChartById(){
        renderJson(analysisService.getUserChartById(UserHolder.getUser().getId()));
    }
    /**
     * 投票页，提交投票
     */
    public void post(@Para List<Long> ls){
        renderJson(analysisService.postForm(UserHolder.getUser().getId(),ls));
    }

    /**
     * 是否参与过本轮投票
     */
    public void isPosted(){
        renderJson(analysisService.isPosted(UserHolder.getUser().getId()));
    }
}
