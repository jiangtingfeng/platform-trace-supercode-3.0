package com.jgw.supercodeplatform.trace.dao.mapper1.antchain;

import com.jgw.supercodeplatform.trace.common.model.page.DaoSearch;
import com.jgw.supercodeplatform.trace.dao.CommonSql;
import com.jgw.supercodeplatform.trace.pojo.antchain.AntChainInfo;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

/**
 * @Auther: chenyu
 * @Date: 2019/3/7 16:28
 * @Description: 以NodeBlockChainInfoMapper为模板，去掉InterfaceId
 */
@Mapper
public interface AntChainMapper extends CommonSql {
    static String allFields="BlockChainId blockChainId,ProductId productId,ProductName productName,TraceBatchInfoId traceBatchInfoId,TraceBatchName traceBatchName"
            + ",FunctionId functionId,FunctionName functionName,NodeInfo nodeInfo,NodeInfoId nodeInfoId,BlockNo blockNo,BlockHash blockHash,TransactionHash transactionHash"
            + ",DATE_FORMAT(TransactionTime,'%Y-%m-%d %H:%i:%S') transactionTime,DATE_FORMAT(CmtTime,'%Y-%m-%d %H:%i:%S') cmtTime,OrganizationId organizationId,OrganizationName organizationName";

    static String listFields="ProductName productName,TraceBatchInfoId traceBatchInfoId,TraceBatchName traceBatchName,DATE_FORMAT(CmtTime,'%Y-%m-%d %H:%i:%S') cmtTime";

    static String whereSelectTraceFunTemplate =
            "<where>" +
                    "<choose>" +
                    //高级搜索
                    "<when test='daoSearch.flag != null and daoSearch.flag == 0'>" +
                    "<if test='daoSearch.params.traceTemplateName != null and daoSearch.params.traceTemplateName != &apos;&apos;'> AND  trace_funtemplatestatistical.TraceTemplateName like &apos;%${daoSearch.params.traceTemplateName}%&apos; </if>" +
                    "<if test='daoSearch.params.nodeCount != null and daoSearch.params.nodeCount != &apos;&apos;'> AND trace_funtemplatestatistical.nodeCount like &apos;%${daoSearch.params.nodeCount}%&apos; </if>" +
                    "<if test='daoSearch.params.traceBatchId != null and daoSearch.params.traceBatchId != &apos;&apos;'> AND trace_funtemplatestatistical.traceBatchId like &apos;%${daoSearch.params.traceBatchId}%&apos; </if>" +
                    "<if test='daoSearch.params.createMan != null and daoSearch.params.createMan != &apos;&apos;'> AND trace_funtemplatestatistical.createMan like &apos;%${daoSearch.params.createMan}%&apos; </if>" +
                    "</when>" +
                    //普通搜索
                    "<when test='daoSearch.flag != null and daoSearch.flag == 1'>" +
                    "<if test='daoSearch.search !=null and daoSearch.search != &apos;&apos;'>" +
                    " AND (" +
                    " ProductName LIKE CONCAT('%',#{daoSearch.search},'%')  " +
                    " OR TraceBatchName LIKE CONCAT('%',#{daoSearch.search},'%') " +
                    ")" +
                    "</if>" +
                    "</when>" +
                    //其他，则为默认所有，不进行筛选
                    "<otherwise>" +
                    "</otherwise>" +
                    "</choose>" +
                    "<if test='organizationId !=null and organizationId != &apos;&apos; '> AND OrganizationId = #{organizationId}</if>"+
                    "</where>";


    @Insert(startScript
            + " INSERT into trace_antblockchain_transaction "
            + " <trim prefix='(' suffix=')' suffixOverrides=','>"
            + "<if test='productId != null and productId != &apos;&apos;'>ProductId,</if> "
            + "<if test='productName != null and productName !=  &apos;&apos; '>ProductName,</if> "
            + "<if test='traceBatchInfoId != null and traceBatchInfoId != &apos;&apos;'>TraceBatchInfoId,</if> "
            + "<if test='traceBatchName != null and traceBatchName != &apos;&apos;'>TraceBatchName,</if> "
            + "<if test='functionId != null and functionId != &apos;&apos;'>FunctionId,</if> "
            + "<if test='functionName != null and functionName != &apos;&apos;'>FunctionName,</if> "
            + "<if test='nodeInfo != null and nodeInfo != &apos;&apos;'>NodeInfo,</if> "
            + "<if test='nodeInfoId != null and nodeInfoId != &apos;&apos;'>nodeInfoId,</if> "
            + "<if test='blockNo != null and blockNo != &apos;&apos;'>BlockNo,</if> "
            + "<if test='blockHash != null and blockHash != &apos;&apos;'>BlockHash,</if> "
            + "<if test='transactionHash != null and transactionHash != &apos;&apos;'>TransactionHash,</if> "
            + "<if test='transactionTime != null '>TransactionTime,</if> "
            + "cmtTime, "
            + "<if test='organizationId != null and organizationId != &apos;&apos;'>OrganizationId,</if> "
            + "<if test='organizationName != null and organizationName != &apos;&apos;'>OrganizationName</if> "
            + "</trim>"
            + "<trim prefix='values (' suffix=')' suffixOverrides=','>"
            + "<if test='productId != null and productId != &apos;&apos;'>#{productId},</if> "
            + "<if test='productName != null and productName !=  &apos;&apos; '>#{productName},</if> "
            + "<if test='traceBatchInfoId != null and traceBatchInfoId != &apos;&apos;'>#{traceBatchInfoId},</if> "
            + "<if test='traceBatchName != null and traceBatchName != &apos;&apos;'>#{traceBatchName},</if> "
            + "<if test='functionId != null and functionId != &apos;&apos;'>#{functionId},</if> "
            + "<if test='functionName != null and functionName != &apos;&apos;'>#{functionName},</if> "
            + "<if test='nodeInfo != null and nodeInfo != &apos;&apos;'>#{nodeInfo},</if> "
            + "<if test='nodeInfoId != null and nodeInfoId != &apos;&apos;'>nodeInfoId,</if> "
            + "<if test='blockNo != null and blockNo != &apos;&apos;'>#{blockNo},</if> "
            + "<if test='blockHash != null and blockHash != &apos;&apos;'>#{blockHash},</if> "
            + "<if test='transactionHash != null and transactionHash != &apos;&apos;'>#{transactionHash},</if> "
            + "<if test='transactionTime != null '>#{transactionTime},</if> "
            + "now(), "
            + "<if test='organizationId != null and organizationId != &apos;&apos;'>#{organizationId},</if> "
            + "<if test='organizationName != null and organizationName != &apos;&apos;'>#{organizationName}</if> "
            + "</trim>"
            + endScript)
    @Options(useGeneratedKeys = true, keyProperty = "blockChainId")
    int insert(AntChainInfo nodeBlockChainInfo);

    @Update("UPDATE trace_antblockchain_transaction SET TransactionHash = #{txHash},TransactionTime =  #{txTime}," +
            "BlockNo = #{blockNo},BlockHash = #{blockHash}  WHERE BlockChainId = #{id}")
    void updateTx(@Param("id") Long id, @Param("txHash") String txHash,  @Param("blockHash")String blockHash, @Param("txTime")Date txTime, @Param("blockNo")Long blockNo);

    @Select(" <script>"
            + " select "+allFields+",count(TraceBatchInfoId) blockNum  from  trace_antblockchain_transaction "
            + whereSelectTraceFunTemplate
            + "  GROUP BY  TraceBatchInfoId order by CmtTime desc"
            + " <if test='daoSearch.startNumber != null and daoSearch.pageSize != null and daoSearch.pageSize != 0'> LIMIT #{daoSearch.startNumber},#{daoSearch.pageSize}</if>"
            + " </script>")
    List<AntChainInfo> list(@Param("daoSearch") DaoSearch searchParams, @Param("organizationId") String organizationId);


    @Select(" <script>"
            + " select count(*) from (select count(*)  from  trace_antblockchain_transaction "
            + whereSelectTraceFunTemplate
            + "  GROUP BY  TraceBatchInfoId ) a"
            + " </script>")
    int count(@Param("daoSearch") DaoSearch searchParams, @Param("organizationId") String organizationId);

    @Select("select "+allFields+" from  trace_antblockchain_transaction where TraceBatchInfoId=#{traceBatchInfoId} order by CmtTime desc")
    List<AntChainInfo> queryByTraceBatchInfoId(@Param("traceBatchInfoId") String traceBatchInfoId);

    /**
     * 查询未上链的信息
     * @return
     */
    @Select("select "+allFields+" from  trace_antblockchain_transaction where TransactionHash is null and CmtTime < #{cmtTime}")
    List<AntChainInfo> queryUnchain(Date cmtTime);
}
