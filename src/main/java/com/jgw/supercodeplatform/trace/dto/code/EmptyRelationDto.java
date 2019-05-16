package com.jgw.supercodeplatform.trace.dto.code;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author liujianqiang
 * @Date 2019/5/16
 * @Description 清空码关联数据结构
 **/
@Data
public class EmptyRelationDto {

    @ApiModelProperty(value = "唯一id")
    @NotNull
    public long id;//唯一id
    @ApiModelProperty(value = "关联方式")
    @NotNull
    public String relationType;//关联方式

}
