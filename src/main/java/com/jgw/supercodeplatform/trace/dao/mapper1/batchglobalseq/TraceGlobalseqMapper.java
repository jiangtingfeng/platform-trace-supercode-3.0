package com.jgw.supercodeplatform.trace.dao.mapper1.batchglobalseq;



import com.jgw.supercodeplatform.trace.pojo.globalseq.CodeGlobalseq;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;


/**
 * 全局序列编码表
 * @author liujianqiang
 * @date 2018年1月12日
 */
@Mapper
public interface TraceGlobalseqMapper {
	/**
	 * 全局序列编码表
	 * @author liujianqiang
	 * @data 2018年1月12日
	 * @return
	 */
	@Select("SELECT a.Keystype AS keysType,a.CurrentMax AS currentMax,a.ExpectedMax AS expectedMax,"
			+ "a.IntervaData AS intervaData,a.NeardataDiffer AS neardataDiffer FROM trace_globalseq a")
	List<CodeGlobalseq> getCodeGlobalseq();
	
	/**
	 * 根据keysType修改全局序列编码表信息
	 * @author liujianqiang
	 * @data 2018年1月12日
	 * @param codeGlobalseq
	 */
	@Update("UPDATE trace_globalseq SET CurrentMax = #{currentMax}, ExpectedMax = #{expectedMax} "
			+ "WHERE Keystype = #{keysType}")
	int updateCodeGlobalseq(CodeGlobalseq codeGlobalseq);
	
}
