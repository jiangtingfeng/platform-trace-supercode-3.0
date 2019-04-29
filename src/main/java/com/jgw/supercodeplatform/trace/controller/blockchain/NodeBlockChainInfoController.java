package com.jgw.supercodeplatform.trace.controller.blockchain;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jgw.supercodeplatform.trace.common.model.RestResult;
import com.jgw.supercodeplatform.trace.common.model.page.AbstractPageService.PageResults;
import com.jgw.supercodeplatform.trace.common.model.page.DaoSearch;
import com.jgw.supercodeplatform.trace.dto.blockchain.CheckNodeBlockInfoParam;
import com.jgw.supercodeplatform.trace.service.blockchain.NodeBlockChainInfoService;
import com.jgw.supercodeplatform.trace.vo.NodeBlockChainInfoListVO;
import com.jgw.supercodeplatform.trace.vo.NodeBlockChainInfoVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/trace/node/blockchain")
@CrossOrigin(origins = "*", maxAge = 3600)
@Api(tags = "区块链接口")
public class NodeBlockChainInfoController {

	@Autowired
	private NodeBlockChainInfoService service;
	
	@RequestMapping(value="/page",method=RequestMethod.GET)
	@ApiOperation(value = "区块链列表接口", notes = "")
	@ApiImplicitParam(paramType="header",value = "新平台token--开发联调使用",name="super-token")
	public RestResult<PageResults<List<NodeBlockChainInfoListVO>>> page(DaoSearch searchParams) throws Exception {
		if(searchParams.getFlag()==null) searchParams.setFlag(1);
		PageResults<List<NodeBlockChainInfoListVO>> listPageResults = service.listSearchViewLike(searchParams);
		RestResult<PageResults<List<NodeBlockChainInfoListVO>>> reslt=new RestResult<PageResults<List<NodeBlockChainInfoListVO>>>();
		reslt.setResults(listPageResults);
		reslt.setMsg("成功");
		reslt.setState(200);
		return reslt;
	}
	
	@RequestMapping(value="/getNodeBlockInfo",method=RequestMethod.GET)
	@ApiOperation(value = "根据批次唯一id查找上链信息接口", notes = "")
	@ApiImplicitParams(value= {@ApiImplicitParam(paramType="query",value = "批次唯一id",name="traceBatchInfoId",required=true),@ApiImplicitParam(paramType="header",value = "新平台token--开发联调使用",name="super-token")})
	public RestResult<List<NodeBlockChainInfoVO>> nodeBlockInfo(@RequestParam(required=true) String traceBatchInfoId) throws Exception {
		return service.queryByTraceBatchInfoId(traceBatchInfoId);
	}
	
	@RequestMapping(value="/checkNodeBlockInfo",method=RequestMethod.POST)
	@ApiOperation(value = "根据批次唯一id校验上链信息接口", notes = "")
	@ApiImplicitParams(value= {@ApiImplicitParam(paramType="header",value = "新平台token--开发联调使用",name="super-token")})
	public RestResult<Map<String, String>> checkNodeBlockInfo(@RequestBody CheckNodeBlockInfoParam checkNodeBlockInfoParam) throws Exception {
		return service.checkNodeBlockInfo(checkNodeBlockInfoParam);
	}
}
