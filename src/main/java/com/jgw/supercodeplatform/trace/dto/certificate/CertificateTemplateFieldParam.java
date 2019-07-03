package com.jgw.supercodeplatform.trace.dto.certificate;

import io.swagger.annotations.ApiModelProperty;

public class CertificateTemplateFieldParam {
    private Long id;

    @ApiModelProperty(value = "模板id")
    private String templateId;

    @ApiModelProperty(value = "组件类型：文本为0，文本框为1，选择框为2，日期为3，图片为4")
    private Integer fieldType;

    @ApiModelProperty(value = "文本、选择框内容")
    private String fieldText;

    @ApiModelProperty(value = "字号")
    private Integer fontSize;

    @ApiModelProperty(value = "加粗")
    private String fontWeight;

    @ApiModelProperty(value = "下划线")
    private String textDecoration;

    @ApiModelProperty(value = "位置 X px")
    private Integer marginLeft;

    @ApiModelProperty(value = "位置 Y px")
    private Integer marginTop;

    @ApiModelProperty(value = "宽度px")
    private Integer width;

    @ApiModelProperty(value = "高度px")
    private Integer height;

    @ApiModelProperty(value = "字段索引，合格证字段数据通过该索引从集合中获取")
    private Integer fieldIndex;

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
        this.templateId = templateId == null ? null : templateId.trim();
    }

    public Integer getFieldType() {
        return fieldType;
    }

    public void setFieldType(Integer fieldType) {
        this.fieldType = fieldType;
    }

    public String getFieldText() {
        return fieldText;
    }

    public void setFieldText(String fieldText) {
        this.fieldText = fieldText == null ? null : fieldText.trim();
    }

    public Integer getFontSize() {
        return fontSize;
    }

    public void setFontSize(Integer fontSize) {
        this.fontSize = fontSize;
    }

    public String getFontWeight() {
        return fontWeight;
    }

    public void setFontWeight(String fontWeight) {
        this.fontWeight = fontWeight;
    }

    public String getTextDecoration() {
        return textDecoration;
    }

    public void setTextDecoration(String textDecoration) {
        this.textDecoration = textDecoration;
    }

    public Integer getMarginLeft() {
        return marginLeft;
    }

    public void setMarginLeft(Integer marginLeft) {
        this.marginLeft = marginLeft;
    }

    public Integer getMarginTop() {
        return marginTop;
    }

    public void setMarginTop(Integer marginTop) {
        this.marginTop = marginTop;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getFieldIndex() {
        return fieldIndex;
    }

    public void setFieldIndex(Integer fieldIndex) {
        this.fieldIndex = fieldIndex;
    }
}