package com.jgw.supercodeplatform.trace.pojo.globalseq;

import lombok.Data;

import java.io.Serializable;

/**
 * 全局序列编码表实体类
 * @author liujianqiang
 * @date 2018年1月17日
 */
@Data
public class CodeGlobalseq implements Serializable{
	private static final long serialVersionUID = 6911117287103607100L;
	private String keysType;
	private long currentMax;
	private long expectedMax;
	private long intervaData;
	private long neardataDiffer;

}
