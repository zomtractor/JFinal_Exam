package pkg.service;

import pkg.bean.Result;

import java.io.File;

public interface FileService {
    Result uploadUserAvatar(File file);

    Result uploadDishImg(File file);
}
