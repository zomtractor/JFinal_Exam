package pkg.bean;

import lombok.Data;

@Data
public class LoginForm {
    private String username;
    private String password;
    private String repassword;
    private String checkcode;

//    public LoginForm(){}
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public String getCheckcode() {
//        return checkcode;
//    }
//
//    public void setCheckcode(String checkcode) {
//        this.checkcode = checkcode;
//    }
//
//    @Override
//    public String toString() {
//        final StringBuilder sb = new StringBuilder("{");
//        sb.append("\"username\":\"")
//                .append(username).append('\"');
//        sb.append(",\"password\":\"")
//                .append(password).append('\"');
//        sb.append(",\"checkcode\":\"")
//                .append(checkcode).append('\"');
//        sb.append('}');
//        return sb.toString();
//    }
}
