package pkg.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

import java.util.Date;
import java.util.Map;

public class TimeStampInterceptor implements Interceptor {
    @Override
    public void intercept(Invocation inv) {
        String[] args = {"timestamp","start","end"};
        boolean debug=false;

        for(String i:args){
            Date date = inv.getController().getDate(i);
            if(date==null) continue;
            debug=true;
            date.setTime(date.getTime()+28800000); //1000*60*60*8
            inv.getController().setAttr(i,date);
        }
        if(debug){
            System.out.println("bug");
        }
        inv.invoke();
    }
}
