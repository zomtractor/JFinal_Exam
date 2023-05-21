package pkg.util;

import com.jfinal.plugin.activerecord.Record;
import pkg.bean.ChartBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 根据v-ChartDatas文档制作的
 */
public class ChartUtil {
    public static ChartBean singleChartData(String xlabel, String ylabel, List<Record> data){
        List<String> index = new ArrayList<>();
        index.add(xlabel);
        index.add(ylabel);
        List<Map<String,?>> res = new ArrayList<>();
        ChartBean data1 = new ChartBean();
        for(int i=0;i<data.size();i++){
            Map<String,Object> mp = new HashMap<>();
            mp.put(xlabel,data.get(i).get("name"));
            mp.put(ylabel,data.get(i).get("value"));
            res.add(mp);
        }
        return new ChartBean(data,index,res);
    }
}
