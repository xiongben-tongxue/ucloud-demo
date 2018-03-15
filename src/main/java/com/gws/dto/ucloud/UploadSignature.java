package com.gws.dto.ucloud;

import lombok.Data;

import java.io.Serializable;

/**
 * 【上传文件签名】
 *
 * @author yangjh  11/05/2017.
 */
@Data
public class UploadSignature implements Serializable{

    private String authorization;

    private String key;

    private String downloadPath;

    private String bucket;

    private String proxySuffix;
}
