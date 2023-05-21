package pkg.bean;

import lombok.Data;

import java.util.List;

@Data
public class Result {
    private Integer code; //0正常
    private String msg;
    private Object data;
    private Integer total;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    private Result(Integer status, String msg, Object data, Integer total) {
        this.code = status;
        this.msg = msg;
        this.data = data;
        this.total = total;
    }
    public boolean isOk(){
        return code==0;
    }
    public static Result ok(String msg,Object data){
        return new Result(0,msg,data,0);
    }
    public static Result ok(){
        return new Result(0, null, null, 0);
    }
    public static Result ok(Object data){
        if(data instanceof List<?>)
            return new Result(0, null, data, ((List<?>) data).size());
        else
            return new Result(0, null, data, 1);
    }

    public static Result ok(List<?> data, Integer total){
        return new Result(0, null, data, total);
    }
    public static Result err(String errorMsg){
        return new Result(1, errorMsg, null, 0);
    }
    public static Result status(boolean res){
        return res?Result.ok():Result.err("boolean false");
    }
    public static Result status(Object obj){
        return obj!=null? Result.ok(obj):Result.err("not found.");
    }
}
