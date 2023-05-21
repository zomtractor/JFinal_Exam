package pkg.bean;

import com.jfinal.plugin.activerecord.Record;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class ChartBean {
    private List<Record> data;
    private Chart chart;

    public ChartBean(List<Record> data,List<String> columns,List<Map<String,?>> rows){
        this.data=data;
        this.chart = new Chart(columns,rows);
    }
}

@Data
@AllArgsConstructor
class Chart{
    private List<String> columns;
    private List<Map<String,?>> rows;
}
