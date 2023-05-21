package pkg.controller.api;

import com.jfinal.aop.Inject;
import com.jfinal.core.Controller;
import com.jfinal.core.Path;
import com.jfinal.upload.UploadFile;
import pkg.service.impl.FileServiceImpl;

@Path("/file/api")
public class FileApiController extends Controller implements ApiController {
    @Inject
    private FileServiceImpl fileService;

    /**
     * 上传菜品照片
     */
    public void uploadDishImg(){
        UploadFile file = getFile();
        renderJson(fileService.uploadDishImg(file.getFile()));
    }

    /**
     * 上传用户头像
     */
    public void uploadUserAvatar(){
        UploadFile file = getFile();
        renderJson(fileService.uploadUserAvatar(file.getFile()));
    }
}
