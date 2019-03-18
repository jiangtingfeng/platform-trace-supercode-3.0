package com.jgw.supercodeplatform.trace.dto.PlatformFun;

import com.jgw.supercodeplatform.trace.pojo.TraceFunFieldConfig;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(value = "功能字段model")
public class TraceFunModel {

    @ApiModelProperty(value = "字段列表")
    private List<TraceFunFieldConfig> traceFunFieldConfigs;

    @ApiModelProperty(name = "功能组件数组")
    private List<FunComponentModel> funComponentModels;


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
