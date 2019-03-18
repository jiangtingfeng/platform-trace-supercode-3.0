package com.jgw.supercodeplatform.trace.dto.PlatformFun;


import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.trace.dto.TraceFunFieldConfigParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel(value = "添加功能model")

public  class CustomizeFun {
    //功能名
    @ApiModelProperty(name = "funName", value = "功能名", required = true)
    private String funName;
    @ApiModelProperty(name = "funId", value = "功能id")
    private String funId;

    //功能介绍
    /*@ApiModelProperty(name = "funIntrod", value = "功能介绍")
    private String funIntrod;

    @ApiModelProperty(name = "funType", value = "功能类型：PLATFORM - 平台功能；SYSTEM - 系统功能", example = "SYSTEM", required = true)
    private FunTypeEnum funType;

    public void setFunType(String funType) throws SuperCodeException {
        if (funType == null || "".equals(funType)) {
            throw new SuperCodeException("功能类型 is null", 500);
        }
        try {
            this.funType = FunTypeEnum.valueOf(funType.toUpperCase());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new SuperCodeException("功能类型传入错误:" + funType, 500);
        }
        if (this.funType == null) {
            throw new SuperCodeException("功能类型传入错误" + funType, 500);
        }
    }

    //功能分组
    @ApiModelProperty(name = "funGroup", value = "功能分类:ANTIFAKE -- 防伪；PPRODUCTION_PROCESS -- 生产过程定制", example = "PPRODUCTION_PROCESS", required = true)
    private FunGroupEnum funGroup;

    public void setFunGroup(String funGroup) throws SuperCodeException {
        if (funGroup == null || "".equals(funGroup)) {
            throw new SuperCodeException("功能分类 is null", 500);
        }
        try {
            this.funGroup = FunGroupEnum.valueOf(funGroup.toUpperCase());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new SuperCodeException("功能分类传入错误" + funGroup, 500);
        }
        if (this.funGroup == null) {
            throw new SuperCodeException("功能分类传入错误" + funGroup, 500);
        }
    }

    //标签
    @ApiModelProperty(name = "labelFlags", value = "功能标签id数组", required = true, hidden = true)
    private String[] labelFlags;

    @ApiModelProperty(name = "funAdscription", value = "", example = "", required = true, hidden = true)
    private FunAdscriptionEnum funAdscription;

    @ApiModelProperty(name = "buttonModels", value = "功能按钮模型数组")
    List<ButtonModel> buttonModels;*/

    @ApiModelProperty(name = "traceFunFieldConfigModel", value = "功能溯源对象模型数组")
    List<TraceFunFieldConfigParam> traceFunFieldConfigModel;

    @ApiModelProperty(name = "功能组件数组")
    List<FunComponent> funComponentModels;

    @ApiModelProperty(value = "批次关联对象")
    private String objectAssociatedType;

    @ApiModelProperty(value = "功能类型：过程节点/控制节点")
    private String regulationType;

    @ApiModelProperty(value = "是否可多次输入")
    private boolean multipleInput;

    @ApiModelProperty(value = "使用场景")
    private int useSceneType;

    @ApiModelProperty(value = "新建批次来源")
    private String createBatchType;

    @ApiModelProperty(value = "批次命名规则链接符")
    private String batchNamedLinkCharacter;

    @ApiModelProperty(value = "批次时间控制")
    private int batchTimeControl;

    @ApiModelProperty(value = "分裂规则")
    private String splittingRule;

    @ApiModelProperty(value = "命名规则字段数组")
    List<BatchNamedRuleField> batchNamedRuleFieldModels;

    public String getFunName() {
        return funName;
    }

    public void setFunName(String funName) {
        this.funName = funName;
    }

    public String getFunId() {
        return funId;
    }

    public void setFunId(String funId) {
        this.funId = funId;
    }

    public List<TraceFunFieldConfigParam> getTraceFunFieldConfigModel() {
        return traceFunFieldConfigModel;
    }

    public void setTraceFunFieldConfigModel(List<TraceFunFieldConfigParam> traceFunFieldConfigModel) {
        this.traceFunFieldConfigModel = traceFunFieldConfigModel;
    }

    public List<FunComponent> getFunComponentModels() {
        return funComponentModels;
    }

    public void setFunComponentModels(List<FunComponent> funComponentModels) {
        this.funComponentModels = funComponentModels;
    }

    public String getObjectAssociatedType() {
        return objectAssociatedType;
    }

    public void setObjectAssociatedType(String objectAssociatedType) {
        this.objectAssociatedType = objectAssociatedType;
    }

    public String getRegulationType() {
        return regulationType;
    }

    public void setRegulationType(String regulationType) {
        this.regulationType = regulationType;
    }

    public boolean isMultipleInput() {
        return multipleInput;
    }

    public void setMultipleInput(boolean multipleInput) {
        this.multipleInput = multipleInput;
    }

    public int getUseSceneType() {
        return useSceneType;
    }

    public void setUseSceneType(int useSceneType) {
        this.useSceneType = useSceneType;
    }

    public String getCreateBatchType() {
        return createBatchType;
    }

    public void setCreateBatchType(String createBatchType) {
        this.createBatchType = createBatchType;
    }

    public String getBatchNamedLinkCharacter() {
        return batchNamedLinkCharacter;
    }

    public void setBatchNamedLinkCharacter(String batchNamedLinkCharacter) {
        this.batchNamedLinkCharacter = batchNamedLinkCharacter;
    }

    public int getBatchTimeControl() {
        return batchTimeControl;
    }

    public void setBatchTimeControl(int batchTimeControl) {
        this.batchTimeControl = batchTimeControl;
    }

    public String getSplittingRule() {
        return splittingRule;
    }

    public void setSplittingRule(String splittingRule) {
        this.splittingRule = splittingRule;
    }

    public List<BatchNamedRuleField> getBatchNamedRuleFieldModels() {
        return batchNamedRuleFieldModels;
    }

    public void setBatchNamedRuleFieldModels(List<BatchNamedRuleField> batchNamedRuleFieldModels) {
        this.batchNamedRuleFieldModels = batchNamedRuleFieldModels;
    }
}


