package pkg.service.impl;

import org.junit.Assert;
import org.junit.Test;
import pkg.bean.Result;
import pkg.service.FileService;

import java.io.File;

public class FileServiceTest {
    FileService service = new FileServiceImpl();
    @Test
    public void uploadUserAvatar() {
        File file = new File("C:/Users/90828/OneDrive/Pictures/avatar2.jpg");
        Result res = service.uploadUserAvatar(file);
        Assert.assertTrue(res.isOk());
    }

    @Test
    public void uploadDishImg() {
        File file = new File("C:/Users/90828/OneDrive/Pictures/avatar2.jpg");
        Result res = service.uploadDishImg(file);
        Assert.assertTrue(res.isOk());
    }
}
