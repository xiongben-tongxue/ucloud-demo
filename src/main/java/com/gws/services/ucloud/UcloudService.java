package com.gws.services.ucloud;

import org.springframework.web.multipart.MultipartFile;

/**
 * 【功能描述】
 *
 * @author yangjh  31/07/2017.
 */
public interface UcloudService {

    String upload(MultipartFile file, String bucket);

}
