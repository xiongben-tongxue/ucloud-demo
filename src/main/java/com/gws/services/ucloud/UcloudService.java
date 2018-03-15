package com.gws.services.ucloud;

import com.gws.dto.ucloud.UploadSignature;
import com.gws.enums.ucloud.FileTypeEnum;
import org.springframework.web.multipart.MultipartFile;

/**
 * 【功能描述】
 *
 * @author yangjh  31/07/2017.
 */
public interface UcloudService {

    String upload(MultipartFile file);

}
