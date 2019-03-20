package com.jgw.supercodeplatform.trace.runner;

import com.jgw.supercodeplatform.trace.common.util.CommonUtilComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(value = 1)//多个CommandLineRunner的执行顺序，1最高
public class BeforeApplicationStartUp implements CommandLineRunner {
    @Autowired
    private CommonUtilComponent commonUtilComponent;

    @Override
    public void run(String... args) throws Exception {
        commonUtilComponent.putUniqSeqToRedis();//启动前放入全局序列数据
    }
}
