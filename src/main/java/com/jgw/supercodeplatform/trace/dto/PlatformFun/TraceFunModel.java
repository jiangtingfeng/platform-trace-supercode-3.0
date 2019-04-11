package com.jgw.supercodeplatform.trace.dto.PlatformFun;

import com.jgw.supercodeplatform.trace.pojo.TraceFunFieldConfig;
import com.jgw.supercodeplatform.trace.pojo.tracefun.TraceBatchNamed;
import com.jgw.supercodeplatform.trace.pojo.tracefun.TraceFunRegulation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(value = "功能字段model")
public class TraceFunModel {

    @ApiModelProperty(value = "字段列表")
    private List<TraceFunFieldConfig> traceFunFieldConfigs;

    @ApiModelProperty(name = "功能组件数组")
    private List<FunComponentModel> funComponentModels;

    private TraceFunRegulation traceFunRegulation;

    public TraceFunRegulation getTraceFunRegulation() {
        return traceFunRegulation;
    }

    public void setTraceFunRegulation(TraceFunRegulation traceFunRegulation) {
        this.traceFunRegulation = traceFunRegulation;
    }

    public List<TraceBatchNamed> getTraceBatchNameds() {
        return traceBatchNameds;
    }

    public void setTraceBatchNameds(List<TraceBatchNamed> traceBatchNameds) {
        this.traceBatchNameds = traceBatchNameds;
    }

    private List<TraceBatchNamed> traceBatchNameds;


    public List<TraceFunFieldConfig> getTraceFunFieldConfigs() {
        return traceFunFieldConfigs;
    }

    public void setTraceFunFieldConfigs(List<TraceFunFieldConfig> traceFunFieldConfigs) {
        this.traceFunFieldConfigs = traceFunFieldConfigs;
    }

    public List<FunComponentModel> getFunComponentModels() {
        return funComponentModels;
    }

    public void setFunComponentModels(List<FunComponentModel> funComponentModels) {
        this.funComponentModels = funComponentModels;
    }
}
