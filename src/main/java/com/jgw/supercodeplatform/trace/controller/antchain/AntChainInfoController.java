package com.jgw.supercodeplatform.trace.controller.antchain;

import com.jgw.supercodeplatform.trace.common.model.RestResult;
import com.jgw.supercodeplatform.trace.common.model.page.AbstractPageService.PageResults;
import com.jgw.supercodeplatform.trace.common.model.page.DaoSearch;
import com.jgw.supercodeplatform.trace.dto.blockchain.CheckNodeBlockInfoParam;
import com.jgw.supercodeplatform.trace.service.antchain.AntChainInfoService;
import com.jgw.supercodeplatform.trace.vo.NodeBlockChainInfoListVO;
import com.jgw.supercodeplatform.trace.vo.NodeBlockChainInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/trace/node/antchain")
@CrossOrigin(origins = "*", maxAge = 3600)
@Api(tags = "蚂蚁区块链接口")
public class AntChainInfoController {

	@Autowired
	private AntChainInfoService service;
	
	@RequestMapping(value="/page",method=RequestMethod.GET)
	@ApiOperation(value = "蚂蚁区块链列表接口", notes = "")
	@ApiImplicitParam(paramType="header",value = "新平台token--开发联调使用",name="super-token")
	public RestResult<PageResults<List<NodeBlockChainInfoListVO>>> page(DaoSearch searchParams) throws Exception {
		PageResults<List<NodeBlockChainInfoListVO>> listPageResults = service.listSearchViewLike(searchParams);
		RestResult<PageResults<List<NodeBlockChainInfoListVO>>> reslt=new RestResult<PageResults<List<NodeBlockChainInfoListVO>>>();
		reslt.setResults(listPageResults);
		reslt.setMsg("成功");
		reslt.setState(200);
		return reslt;
	}
	
	@RequestMapping(value="/getNodeBlockInfo",method=RequestMethod.GET)
	@ApiOperation(value = "根据批次唯一id查找蚂蚁区块链上链信息接口", notes = "")
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

	@RequestMapping(value="/reCoChain",method=RequestMethod.POST)
	@ApiOperation(value = "定时任务，上链失败的记录重新上链", notes = "")
	public RestResult<String> reCoChain(){
		service.reCoChain();
		RestResult<String> restResult = new RestResult<>();
		restResult.setState(200);
		restResult.setMsg("成功");
		return restResult;
	}
}
