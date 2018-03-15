package com.gws.controllers.ucloud;

import com.gws.controllers.BaseController;
import com.gws.controllers.JsonResult;
import com.gws.services.ucloud.UcloudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * ucloud的处理文件的接口
 */
@RestController
@RequestMapping("/ucloud/file/")
public class UcloudController extends BaseController {

    @Autowired
    private UcloudService ucloudService;

    /**
     * 单文件上传
     * @param file
     * @param bucket
     * @return
     */
    @RequestMapping("uploadFile")
    public JsonResult uploadFile(@RequestParam("file") MultipartFile file,String bucket){

        String result = ucloudService.uploadFile(file, bucket);

        return success(result);
    }

    /**
     * 多文件上传
     * @param files
     * @param bucket
     * @return
     */
    @RequestMapping("uploadFiles")
    public JsonResult uploadFiles(@RequestParam("files") MultipartFile[] files,String bucket){

        List<String> result = ucloudService.uploadFiles(files, bucket);

        return success(result);
    }
}
