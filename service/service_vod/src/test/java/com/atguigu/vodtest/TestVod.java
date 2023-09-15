package com.atguigu.vodtest;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;

import java.util.List;

/**
 * @author AOA
 * @version 1.0
 * @description: TODO
 * @date 2023/7/31 19:19
 */

public class TestVod {
    public static void main(String[] args) throws ClientException {
        String accessKeyId = "LTAI5t6s6iDmJQVQzS7oJaRC";
        String accessKeySecret = "977W5s4nX1nruNiKtjlrE7vl5zEDYK";
        String title = "Abc";
        String fileName = "D:/6 - What If I Want to Move Faster.mp4";

        //本地上传视频
        UploadVideoRequest request = new UploadVideoRequest(accessKeyId, accessKeySecret, title, fileName);
        /* 可指定分片上传时每个分片的大小，默认为2M字节 */
        request.setPartSize(2 * 1024 * 1024L);
        /* 可指定分片上传时的并发线程数，默认为1，(注：该配置会占用服务器CPU资源，需根据服务器情况指定）*/
        request.setTaskNum(1);

        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadVideoResponse response = uploader.uploadVideo(request);
        System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
        if (response.isSuccess()) {
            System.out.print("VideoId=" + response.getVideoId() + "\n");
        } else {
            /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }
    }

    //获取视频地址
    public static void getPlayUrl() throws ClientException {
        //使用视频ID来获取视频播放地址

        //1.创建初始化对象
        DefaultAcsClient defaultAcsClient = InitVodClient.initVodClient("LTAI5t6s6iDmJQVQzS7oJaRC","977W5s4nX1nruNiKtjlrE7vl5zEDYK" );
        //2.获取视频地址的request和response
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        GetPlayInfoResponse response = new GetPlayInfoResponse();
        //3.向request设置ID
        request.setVideoId("19d3f6002f9871eebfe80764a3fc0102");
        //4.调用初始化对象，传递request, 获取数据
        response = defaultAcsClient.getAcsResponse(request);

        //获取视频信息的集合
        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();

        //获取每一个的url
        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
            System.out.println("playInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
        }
        //获取base信息
        System.out.println("VideoTitle = " + response.getVideoBase().getTitle() + "\n");
    }

    //获取视频凭证
    public static void getVideoAuth() throws ClientException {
        DefaultAcsClient client = InitVodClient.initVodClient("LTAI5t6s6iDmJQVQzS7oJaRC", "977W5s4nX1nruNiKtjlrE7vl5zEDYK");

        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();

        request.setVideoId("19d3f6002f9871eebfe80764a3fc0102");

        response = client.getAcsResponse(request);

        //视频凭证
        String playAuth = response.getPlayAuth();

        System.out.println("视频凭证 = " + playAuth);
    }
}
