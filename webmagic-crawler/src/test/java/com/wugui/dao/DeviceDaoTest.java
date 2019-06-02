package com.wugui.dao;


import com.alibaba.fastjson.JSONObject;
import com.wugui.Application;
import com.wugui.pojo.DeviceEntity;
import com.wugui.pojo.JobInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;


@ActiveProfiles({"pg"})// 选择激活的profile
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@Slf4j
public class DeviceDaoTest {


    @Autowired
    private DeviceDao deviceDao;

    @Test
    public void test() {

        JobInfo jobInfo = new JobInfo();
        jobInfo.setCompanyName("广州网易");
        jobInfo.setCompanyAddr("广州天河区");
        jobInfo.setCompanyInfo("游戏之美");
        jobInfo.setJobName("Java工程师");
        jobInfo.setJobAddr("");
        jobInfo.setJobInfo("会写Java代码的程序员，会vue");
        jobInfo.setSalaryMin(1000);
        jobInfo.setSalaryMax(8000);


        DeviceEntity deviceEntity = new DeviceEntity();
        deviceEntity.setDeviceNum("77777");
        deviceEntity.setJsonb(JSONObject.toJSONString(jobInfo));
        deviceDao.save(deviceEntity);
    }
}