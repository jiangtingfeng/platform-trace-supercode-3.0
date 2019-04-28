package com.jgw.supercodeplatform.project.zaoyangpeach.schedule;

import com.jgw.supercodeplatform.trace.dao.mapper1.storedprocedure.DataSyncMapper;
import com.jgw.supercodeplatform.trace.service.tracefun.TraceBatchRelationEsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Configuration
//打开quartz定时器总开关
@EnableScheduling
public class DataSyncSchedule {

    private static Logger logger= LoggerFactory.getLogger(DataSyncSchedule.class);

    @Autowired
    private DataSyncMapper dataSyncMapper;

    @Scheduled(cron = "0 0 18 * * ?")
    public void myTest1() {
        logger.debug("开始同步:");

        Map<String,String> params=new HashMap<String,String>();
        params.put("startdate","2019-04-20");
        //dataSyncMapper.execute(params);

        logger.debug("同步完成");
    }
}