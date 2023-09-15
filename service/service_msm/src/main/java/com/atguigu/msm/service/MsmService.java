package com.atguigu.msm.service;

import java.util.Map;

public interface MsmService {

    boolean send(Map<String, String> map, String phone);
}
