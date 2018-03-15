package com.gws.services.ucloud;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 【功能描述】
 *
 * @author yangjh  31/07/2017.
 */
public interface UcloudService {

    /**
     * 上传单个文件
     * @param file
     * @param bucket
     * @return
     */
    String uploadFile(MultipartFile file, String bucket);

    /**
     * 上传多个文件
     * @param files
     * @param bucket
     * @return
     */
    List<String> uploadFiles(MultipartFile[] files, String bucket);
}
