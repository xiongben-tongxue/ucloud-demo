package com.gws.services.ucloud.impl;

import cn.ucloud.ufile.*;
import cn.ucloud.ufile.sender.PutSender;
import com.gws.dto.ucloud.UploadSignature;
import com.gws.enums.ucloud.FileTypeEnum;
import com.gws.services.ucloud.UcloudService;
import com.gws.utils.GwsLogger;
import org.apache.http.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

    @Value("${ucloud.bucket}")
    private String bucket;


    private static HmacSHA1 hmacSHA1 = new HmacSHA1();


    @Override
    public String upload(MultipartFile file) {

        UFileConfig.getInstance().setUcloudPublicKey(ucloudPublicKey);
        UFileConfig.getInstance().setUcloudPrivateKey(ucloudPrivateKey);
        UFileConfig.getInstance().setProxySuffix(proxySuffix);
        UFileConfig.getInstance().setDownloadProxySuffix(proxySuffix);

        if (file.isEmpty()) {
            return null;
        }

        StringBuilder key = new StringBuilder();

        String fileName = file.getOriginalFilename();
        String postfix = getPostfix(fileName);
        String filePath = "/Users/wangdong/Pictures/InternetPicture/" + fileName;


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
        request.setFilePath(filePath);


        UFileClient ufileClient = null;

        try {
            ufileClient = new UFileClient();
            putFile(ufileClient, request);
        } finally {
            ufileClient.shutdown();
        }

        return null;
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

