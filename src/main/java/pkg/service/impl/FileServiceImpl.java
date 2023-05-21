package pkg.service.impl;

import pkg.bean.Result;
import pkg.service.FileService;
import pkg.util.FileUtil;

import java.io.File;

public class FileServiceImpl implements FileService {
    @Override
    public Result uploadUserAvatar(File file){
        String s = FileUtil.saveImg(file,"/user/");
        if(s==null) return Result.err("error");
        return Result.ok(s);
    }
    @Override
    public Result uploadDishImg(File file){
        String s = FileUtil.saveImg(file,"/dish/");
        if(s==null) return Result.err("error");
        return Result.ok(s);
    }
}
