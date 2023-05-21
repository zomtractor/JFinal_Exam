package pkg.bean;

import lombok.Data;

@Data
public class Pagination {
    private Object data;
    private Integer totalRows;
    private Integer pageSize;
    private Integer currentPage;

}
