package com.sky.utils;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@Slf4j
public class QiniuOssUtil {
    private String accessKey;
    private String secretKey;
    private String bucket;
    private String domain;

    public String upload(String fileName, InputStream file) {
//        构造一个带指定Region对象的配置类
        Configuration configuration = new Configuration(Region.regionAs0());
        configuration.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;
        UploadManager uploadManager = new UploadManager(configuration);

        fileName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd/")) + fileName;
        Auth auth = Auth.create(accessKey, secretKey);
        String uploadToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(file, fileName, uploadToken, null, null);

            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
        return domain + fileName;
    }

}
