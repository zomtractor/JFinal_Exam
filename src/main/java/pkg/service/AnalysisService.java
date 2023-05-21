package pkg.service;

import pkg.bean.Result;

import java.util.Date;
import java.util.List;

public interface AnalysisService {
    Result getDishChartByDate(Date d1, Date d2);

    Result getDishChartById(Long id, Date start, Date end);

    Result getUserChartById(Long id);

    Result getUserChartByDate(Long id, Date start, Date end);

    Result getDishChartByRedis();

    Result publishNewTest(Date date);

    Result getHistoryById();

    Result postForm(Long id, List<Long> ls);

    Result isPosted(Long id);
}
