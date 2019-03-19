package com.jgw.supercodeplatform.trace.controller.tracebatch;

import com.jgw.supercodeplatform.trace.common.util.CommonUtil;
import com.jgw.supercodeplatform.trace.service.tracebatchname.TraceBatchNamedService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * liushui批次controller
 *
 * @author wang
 * @date 2018年12月12日
 */
@RestController
@RequestMapping("/trace/batch/name")
@Api(tags = "流水生成")
public class TraceBatchNamedController extends CommonUtil {


   @Autowired
   private TraceBatchNamedService traceBatchNamedService;
    /**
     * 流水批次名称
     * @return
     * @throws Exception
     * @author wangwenzhang
     * @data 2018年3月18日
     */

    @RequestMapping("/liushui")
    public String GetBatchName1(String funId, String funName) throws Exception {
        String str = traceBatchNamedService.queryOneTraceBatchnamed(funId, funName);

        return str;
    }

}

