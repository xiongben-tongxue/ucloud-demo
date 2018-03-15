package com.gws.controllers.ucloud;

import com.gws.controllers.BaseController;
import com.gws.controllers.JsonResult;
import com.gws.services.ucloud.UcloudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * ucloud的处理文件的接口
 */
@RestController
@RequestMapping("/ucloud/file/")
public class UcloudController extends BaseController {

    @Autowired
    private UcloudService ucloudService;

    @RequestMapping("uploadFile")
    public JsonResult uploadFile(@RequestParam("file") MultipartFile file){

        String result = ucloudService.upload(file);

        return success(result);
    }
}
