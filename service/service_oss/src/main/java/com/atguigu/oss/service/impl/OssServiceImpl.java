package com.atguigu.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.common.comm.ResponseMessage;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.atguigu.oss.service.OssService;
import com.atguigu.oss.utils.ConstantPropertiesUtils;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * @author AOA
 * @version 1.0
 * @description: TODO
 * @date 2023/7/18 1:00
 */
@Service
@Slf4j
public class OssServiceImpl implements OssService {

    //上传文件流(头像)
    @Override
    public String uploadAvatar(MultipartFile file) {

        // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
        String endpoint = ConstantPropertiesUtils.END_POINT;

        String accessKeyId = ConstantPropertiesUtils.KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.KEY_SECRET;

        // 填写Bucket名称，例如examplebucket。
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;

        // 生成上传文件的目录
//        String folderName = new SimpleDateFormat("yyyyMMdd").format(new Date());
        //joda依赖
        String folderName = new DateTime().toString("yyyy/MM/dd");

        //文件原始名字
        String fileMainName = file.getOriginalFilename();
        //文件扩展名
        String extensionName = fileMainName.substring(fileMainName.lastIndexOf("."));
        //文件最终名
        String finalName = UUID.randomUUID().toString().replace("-", "") + extensionName;

        //对象名
        String objectName = folderName+ "/" + finalName;

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try {
            InputStream inputStream = file.getInputStream();

            // 创建PutObjectRequest对象。
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, inputStream);
            // 创建PutObject请求。
            ossClient.putObject(putObjectRequest);


            log.info("https://" + bucketName + "." + endpoint + "/" + objectName);
            String url = "https://" + bucketName + "." + endpoint + "/" + objectName;
            return url;

        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());

            return null;
        } catch (IOException e) {
            e.printStackTrace();

            return null;
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }

    }
}
