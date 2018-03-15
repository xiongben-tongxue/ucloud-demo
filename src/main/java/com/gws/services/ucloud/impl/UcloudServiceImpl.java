package com.gws.services.ucloud.impl;

import cn.ucloud.ufile.*;
import cn.ucloud.ufile.sender.PutSender;
import com.gws.services.ucloud.UcloudService;
import com.gws.utils.GwsLogger;
import org.apache.http.Header;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * 【功能描述】
 *
 * @author wangdong
 */
@Configuration
@Service
public class UcloudServiceImpl implements UcloudService {

    @Value("${ucloud.publicKey}")
    private String ucloudPublicKey;

    @Value("${ucloud.privateKey}")
    private String ucloudPrivateKey;

    @Value("${ucloud.proxySuffix}")
    private String proxySuffix;

    @Value("${ucloud.cdn.https.host}")
    private String cdnHttpsHost;

    private String http = "http://";


    private static HmacSHA1 hmacSHA1 = new HmacSHA1();

    @PostConstruct
    public void init() {
        UFileConfig.getInstance().setUcloudPublicKey(ucloudPublicKey);
        UFileConfig.getInstance().setUcloudPrivateKey(ucloudPrivateKey);
        UFileConfig.getInstance().setProxySuffix(proxySuffix);
        UFileConfig.getInstance().setDownloadProxySuffix(proxySuffix);
    }

    @Override
    public String uploadFile(MultipartFile file, String bucket) {

        if (file.isEmpty()) {
            return null;
        }

        StringBuilder key = new StringBuilder();

        String fileName = file.getOriginalFilename();
        String postfix = getPostfix(fileName);

        if (!StringUtils.isEmpty(postfix)) {
            key.append(postfix).append("/");
        }
        key.append(UUID.randomUUID().toString());
        if (!StringUtils.isEmpty(postfix)) {
            key.append(".").append(postfix);
        }

        UFileRequest request = new UFileRequest();
        request.setBucketName(bucket);
        request.setKey(key.toString());

        try {
            request.setInputStream(file.getInputStream());
            request.setContentLength(file.getSize());
        } catch (Exception e) {
            GwsLogger.error(e, "upload error");
        }


        UFileClient ufileClient = null;

        try {
            ufileClient = new UFileClient();
            putFile(ufileClient, request);

        } finally {
            ufileClient.shutdown();
        }

        StringBuffer stringBuffer = new StringBuffer();

        StringBuffer downCdnHttpsHost = stringBuffer.append(http).append(bucket).append(cdnHttpsHost);

        return new StringBuffer().append(downCdnHttpsHost).append("/").append(key).toString();
    }

    /**
     * 上传多个文件
     *
     * @param files
     * @param bucket
     * @return
     */
    @Override
    public List<String> uploadFiles(MultipartFile[] files, String bucket) {

        List<String> result = new ArrayList<>();

        for (MultipartFile file : files) {
            String download = uploadFile(file,bucket);
            result.add(download);
        }
        return CollectionUtils.isEmpty(result) ? Collections.EMPTY_LIST : result;
    }

    private static void putFile(UFileClient ufileClient, UFileRequest request) {
        PutSender sender = new PutSender();
        sender.makeAuth(ufileClient, request);

        UFileResponse response = sender.send(ufileClient, request);
        if (response != null) {

            System.out.println("status line: " + response.getStatusLine());

            Header[] headers = response.getHeaders();
            for (int i = 0; i < headers.length; i++) {
                System.out.println("header " + headers[i].getName() + " : " + headers[i].getValue());
            }

            System.out.println("body length: " + response.getContentLength());

            InputStream inputStream = response.getContent();
            if (inputStream != null) {
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    String s = "";
                    while ((s = reader.readLine()) != null) {
                        System.out.println(s);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
        private String getPostfix (String file){
            if (null == file) {
                return "";
            }
            int postfixIdx = file.lastIndexOf(".");
            if (-1 == postfixIdx) {
                return "";
            } else {
                return file.substring(postfixIdx + 1);
            }
        }
    }

