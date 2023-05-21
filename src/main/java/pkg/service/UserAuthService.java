package pkg.service;


import pkg.bean.Result;

import java.io.OutputStream;

public interface UserAuthService {
    Result verifyCheckCode(String sessionId,String code);

    Result generateCheckCode(String sessionId, OutputStream os);

    Result login(String username,String password);

    Result register(String username, String password);

    Result logOut(String token);
}
