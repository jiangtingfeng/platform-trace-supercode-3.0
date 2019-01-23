package com.jgw.supercodeplatform.trace.controller.template;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jgw.supercodeplatform.trace.common.model.RestResult;
import com.jgw.supercodeplatform.trace.common.model.page.AbstractPageService.PageResults;
import com.jgw.supercodeplatform.trace.common.model.page.DaoSearch;
import com.jgw.supercodeplatform.trace.dto.template.TraceFunTemplateconfigDeleteParam;
import com.jgw.supercodeplatform.trace.dto.template.TraceFunTemplateconfigParam;
import com.jgw.supercodeplatform.trace.dto.template.query.TraceFunTemplateconfigListParam;
import com.jgw.supercodeplatform.trace.dto.template.update.TraceFunTemplateconfigUpdateParam;
import com.jgw.supercodeplatform.trace.service.template.TraceFunTemplatecofigListService;
import com.jgw.supercodeplatform.trace.service.template.TraceFunTemplateconfigService;
import com.jgw.supercodeplatform.trace.vo.TraceFunTemplateconfigVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/trace/traceFunTemplateconfig")
@Api(tags = "追溯模板配置管理")
public class TraceFunTemplateconfigController {

	@Autowired
	private TraceFunTemplateconfigService service;

	@Autowired
	private TraceFunTemplatecofigListService traceFunTemplatecofigListService;
	
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ApiOperation(value = "新增追溯模板接口", notes = "")
	@ApiImplicitParam(paramType="header",value = "新平台token--开发联调使用",name="super-token")
	public RestResult<String> add(@Valid @RequestBody List<TraceFunTemplateconfigParam> templateList) throws Exception{
		return service.add(templateList);
	}
	
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	@ApiOperation(value = "根据模板id和节点功能id删除追溯模板节点接口",hidden=true)
	@ApiImplicitParam(paramType="header",value = "新平台token--开发联调使用",name="super-token")
	public RestResult<String> delete(@Valid @RequestBody TraceFunTemplateconfigDeleteParam param) throws Exception{
		return service.delete(param);
	}
	
	@RequestMapping(value="/deleteById",method=RequestMethod.POST)
	@ApiOperation(value = "根据模板主键id删除追溯模板节点接口")
	@ApiImplicitParams(value= {@ApiImplicitParam(paramType="query",value = "模板主键id",name="id",required=true),@ApiImplicitParam(paramType="header",value = "新平台token--开发联调使用",name="super-token")})
	public RestResult<String> deleteById( @RequestParam(required=true) Long id) throws Exception{
		return service.deleteById(id);
	}
	
	@RequestMapping(value="/update",method=RequestMethod.POST)
	@ApiOperation(value = "修改追溯模板接口", notes = "修改的节点只能是新增的字段")
	@ApiImplicitParam(paramType="header",value = "新平台token--开发联调使用",name="super-token")
	public RestResult<String> update(@Valid @RequestBody TraceFunTemplateconfigUpdateParam traceFunTemplateconfigUpdateParam) throws Exception{
		return service.update(traceFunTemplateconfigUpdateParam);
	}


	@RequestMapping(value="/query",method=RequestMethod.GET)
	@ApiOperation(value = "追溯模板列表接口", notes = "")
	@ApiImplicitParam(paramType="header",value = "新平台token--开发联调使用",name="super-token")
	public RestResult<PageResults<List<TraceFunTemplateconfigListParam>>> query(DaoSearch searchParams) throws Exception {
		PageResults<List<TraceFunTemplateconfigListParam>> listPageResults = service.listSearchViewLike(searchParams);
		RestResult<PageResults<List<TraceFunTemplateconfigListParam>>> reslt=new RestResult<PageResults<List<TraceFunTemplateconfigListParam>>>();
		reslt.setResults(listPageResults);
		reslt.setMsg("成功");
		reslt.setState(200);
		return reslt;
	}

	@RequestMapping(value="/queryByTemplateName",method=RequestMethod.GET)
	@ApiOperation(value = "追溯模板列表只查询模板名称接口", notes = "")
	@ApiImplicitParam(paramType="header",value = "新平台token--开发联调使用",name="super-token")
	public RestResult<PageResults<List<TraceFunTemplateconfigListParam>>> queryByTemplateName(DaoSearch searchParams) throws Exception {
		PageResults<List<TraceFunTemplateconfigListParam>> listPageResults = traceFunTemplatecofigListService.listSearchViewLike(searchParams);
		RestResult<PageResults<List<TraceFunTemplateconfigListParam>>> reslt=new RestResult<PageResults<List<TraceFunTemplateconfigListParam>>>();
		reslt.setResults(listPageResults);
		reslt.setMsg("成功");
		reslt.setState(200);
		return reslt;
	}

	@RequestMapping(value="/list",method=RequestMethod.GET)
	@ApiOperation(value = "追溯模板不分页列表接口", notes = "")
	@ApiImplicitParam(paramType="header",value = "新平台token--开发联调使用",name="super-token")
	public RestResult<List<TraceFunTemplateconfigListParam>> list() throws Exception {
		List<TraceFunTemplateconfigListParam> list = service.selectTemplateByTemplateId();
		RestResult<List<TraceFunTemplateconfigListParam>> reslt=new RestResult<List<TraceFunTemplateconfigListParam>>();
		reslt.setResults(list);
		reslt.setMsg("成功");
		reslt.setState(200);
		return reslt;
	}

	@RequestMapping(value="/listNodes",method=RequestMethod.GET)
	@ApiOperation(value = "顺序查询追溯模板节点接口", notes = "")
	@ApiImplicitParams(value= {@ApiImplicitParam(paramType="query",value = "模板id",name="traceTemplateId",required=true),@ApiImplicitParam(paramType="header",value = "新平台token--开发联调使用",name="super-token")})
	public RestResult<List<TraceFunTemplateconfigVO>> listNodes(String traceTemplateId) throws Exception {
		List<TraceFunTemplateconfigVO> list=service.listNodes(traceTemplateId);
		RestResult<List<TraceFunTemplateconfigVO>> reslt=new RestResult<List<TraceFunTemplateconfigVO>>();
		reslt.setResults(list);
		reslt.setMsg("成功");
		reslt.setState(200);
		return reslt;
	}
    
}
