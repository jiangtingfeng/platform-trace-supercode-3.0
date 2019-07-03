package com.jgw.supercodeplatform.trace.dto.standardtemplate;

import com.jgw.supercodeplatform.trace.dto.TraceFunFieldConfigParam;
import com.jgw.supercodeplatform.trace.dto.template.TraceFunTemplateconfigParam;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

public class StandardTemplateParam {

    private Long id;

    private String templateId;

    @ApiModelProperty(value = "模板类别id")
    private Integer categoryId;

    @ApiModelProperty(value = "模板类别名称")
    private String categoryName;

    @ApiModelProperty(value = "一级类目id")
    private Integer level1TypeId;

    @ApiModelProperty(value = "一级类目名称")
    private String level1TypeName;

    @ApiModelProperty(value = "二级类目id")
    private Integer level2TypeId;

    @ApiModelProperty(value = "二级类目名称")
    private String level2TypeName;

    @ApiModelProperty(value = "模板编号")
    private String templateNumber;

    @ApiModelProperty(value = "创建人")
    private String createMan;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    public String getCreateMan() {
        return createMan;
    }

    public void setCreateMan(String createMan) {
        this.createMan = createMan;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getLevel1TypeId() {
        return level1TypeId;
    }

    public void setLevel1TypeId(Integer level1TypeId) {
        this.level1TypeId = level1TypeId;
    }

    public String getLevel1TypeName() {
        return level1TypeName;
    }

    public void setLevel1TypeName(String level1TypeName) {
        this.level1TypeName = level1TypeName;
    }

    public Integer getLevel2TypeId() {
        return level2TypeId;
    }

    public void setLevel2TypeId(Integer level2TypeId) {
        this.level2TypeId = level2TypeId;
    }

    public String getLevel2TypeName() {
        return level2TypeName;
    }

    public void setLevel2TypeName(String level2TypeName) {
        this.level2TypeName = level2TypeName;
    }

    public String getTemplateNumber() {
        return templateNumber;
    }

    public void setTemplateNumber(String templateNumber) {
        this.templateNumber = templateNumber;
    }

    public Integer getDisableFlag() {
        return disableFlag;
    }

    public void setDisableFlag(Integer disableFlag) {
        this.disableFlag = disableFlag;
    }

    public List<TraceFunTemplateconfigParam> getTraceFunTemplateconfigParams() {
        return traceFunTemplateconfigParams;
    }

    public void setTraceFunTemplateconfigParams(List<TraceFunTemplateconfigParam> traceFunTemplateconfigParams) {
        this.traceFunTemplateconfigParams = traceFunTemplateconfigParams;
    }

    @ApiModelProperty(value = "模板状态：禁用为1，启用为0")
    private Integer disableFlag;

    @ApiModelProperty(value = "手动节点列表")
    private List<TraceFunTemplateconfigParam> traceFunTemplateconfigParams;

}
