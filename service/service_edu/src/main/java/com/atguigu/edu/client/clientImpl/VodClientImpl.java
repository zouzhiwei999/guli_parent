package com.atguigu.edu.client.clientImpl;

import com.atguigu.commomutils.R;
import com.atguigu.edu.client.VodClient;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author AOA
 * @version 1.0
 * @description: TODO
 * @date 2023/8/8 0:35
 */
@Component
public class VodClientImpl implements VodClient {
    @Override
    public R deleteVideo(String id) {
        return R.error().message("删除视频出错");
    }

    @Override
    public R deleteBatch(List<String> VideoIdList) {
        return R.error().message("删除视频出错");
    }
}
