package com.jgw.supercodeplatform.trace.common.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.trace.common.model.ReturnParamsMap;
import com.jgw.supercodeplatform.trace.common.model.page.NormalProperties;
import com.jgw.supercodeplatform.trace.common.model.page.Page;
import com.jgw.supercodeplatform.trace.config.redis.RedisUtil;
import com.jgw.supercodeplatform.trace.constants.RedisKey;
import com.jgw.supercodeplatform.trace.constants.TraceBaseConstants;
import com.jgw.supercodeplatform.trace.dao.dynamicMapper1.DynamicCreateTableMapper1;
import com.jgw.supercodeplatform.trace.exception.SuperCodeTraceException;
import com.jgw.supercodeplatform.user.UserInfoUtil;


/**
 * 公共方法工具包
 * Created by corbe on 2018/9/4.
 */
@Component
public class CommonUtil extends UserInfoUtil {

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected RedisUtil redisUtil;

    /**
     * 获取32位UUID，去掉中间的-
     *
     * @return
     * @author corbett
     * @data 2018年9月4日
     */
    public String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     *  产生6位随机数
     *
     * @return
     * @throws Exception
     * @author liujianqiang
     * @data 2018年9月5日
     */
    public String getSixRandom() throws Exception {
        Random random = new Random();
        int sixRandom = random.nextInt(899999) + 100000;
        String result = "" + sixRandom;
        if (result.length() != 6) {
            throw new Exception("不是六位随机数,number= " + result);
        }
        return result;
    }


    public boolean checkIsMailBox(String mail) {
        if (mail.indexOf("@") < 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 返回restTemplate
     *
     * @return
     * @author liujianqiang
     * @data 2018年9月13日
     */
    public RestTemplate getRestTemplate() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setBufferRequestBody(false);
        requestFactory.setConnectTimeout(2000);
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        return restTemplate;
    }

    /**
     * 验证是否为正整数公共方法
     * 正整数返回true,不是正整数返回false
     *
     * @param number
     * @return
     * @author liujianqiang
     * @data 2018年4月2日
     */
    public boolean checkInt(String number) {
        String regex = "^[1-9]+[0-9]*$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(number);
        if (m.find()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 验证参数是否为空,为空返回true
     *
     * @param obj
     * @return
     * @author liujianqiang
     * @data 2018年10月11日
     */
    public boolean isNull(Object obj) {
        if (obj == null || "".equals(obj.toString())) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 功能描述：将分页信息放入查询map中
     *
     * @return void
     * @Author corbett
     * @Description //TODO
     * @Date 9:14 2018/10/12
     * @Param [searchMap, totalCount]
     **/
    public void pageInSearchMapInit(Map searchMap) {
        searchMap.put("startNumber", 0);
        searchMap.put("pageCount", 0);
    }


    /**
     * 功能描述：转换为date
     *
     * @return java.util.Date
     * @Author corbett
     * @Description //TODO
     * @Date 22:02 2018/10/14
     * @Param [localDateTime]
     **/
    public Date toDate(LocalDateTime localDateTime) {
        localDateTime = localDateTime == null ? LocalDateTime.now() : localDateTime;
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDateTime.atZone(zoneId);
        return Date.from(zdt.toInstant());
    }

    /**
     * 功能描述：转换为date
     *
     * @return java.util.Date
     * @Author corbett
     * @Description //TODO
     * @Date 22:02 2018/10/14
     **/
    public Date toDate() {
        return toDate(LocalDateTime.now());
    }

    /**
     * 功能描述：date change localdatetime
     *
     * @return java.time.LocalDateTime
     * @Author corbett
     * @Description //TODO
     * @Date 9:59 2018/10/15
     * @Param [date]
     **/
    public LocalDateTime toLocalDateTime(Date date) {

        date = date == null ? new Date() : date;
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();

        // atZone()方法返回在指定时区从此Instant生成的ZonedDateTime。
        LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
        return localDateTime;
    }

    /**
     * 功能描述：date change localdatetime
     *
     * @return java.time.LocalDateTime
     * @Author corbett
     * @Description //TODO
     * @Date 9:59 2018/10/15
     **/
    public LocalDateTime toLocalDateTime() {
        return toLocalDateTime(new Date());
    }
    
    /**
     * 统一返回结果list字段名
     * @param returnMap
     * @param list
     * @return
     * @author liujianqiang
     * @data 2018年11月12日
     */
    public Map<String, Object> getRetunMap(Map<String, Object> returnMap, List<?> list) {
        returnMap.put("list", list);
        return returnMap;
    }
    
    /**
     * 转换page类,并放到入参中
     * @param params
     * @param total
     * @return
     * @throws Exception
     * @author liujianqiang
     * @data 2018年11月12日
     */
    public ReturnParamsMap getPageAndRetuanMap(Map<String, Object> params, Integer total) throws SuperCodeTraceException {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        Page page = getPage(params, total);
        params.put("startNumber", page.getStartNumber());//开始数字
        params.put("pageSize", page.getPageSize());//每页记录数
        ReturnParamsMap returnParamsMap = new ReturnParamsMap();
        returnMap.put("pagination", page);
        returnParamsMap.setParamsMap(params);
        returnParamsMap.setReturnMap(returnMap);
        return returnParamsMap;
    }
    
    /**
     * 根据入参和总记录数，返回页码实体类
     * @param params
     * @param total
     * @return
     * @throws Exception
     * @author liujianqiang
     * @data 2018年10月11日
     */
    public Page getPage(Map<String, Object> params, int total) throws SuperCodeTraceException {
        Object pageCountObj = params.get("pageSize");//每页记录数
        Object currentPageObj = params.get("current");//当前页
        int pageCount;
        int currentPage;
        if (isNull(pageCountObj) || Integer.parseInt(pageCountObj.toString()) == 0) {//假如每页记录数为空,默认为10条
            pageCount = NormalProperties.DEFAULT_PAGE_COUNT;
        } else {
            pageCount = Integer.parseInt(pageCountObj.toString());
        }
        if (isNull(currentPageObj) || Integer.parseInt(currentPageObj.toString()) == 0) {//假如当前页为空,则当前页设置为默认值1
            currentPage = NormalProperties.DEFAULT_CURRENT_PAGE;
        } else {
            currentPage = Integer.parseInt(currentPageObj.toString());
        }
        return new Page(pageCount, currentPage, total);
    }
    
    /**
     * 校验参数和参数值为空或者null
     *
     * @param params
     * @param args
     * @throws Exception
     * @author liujianqiang
     * @data 2018年1月17日
     */
    public void validateRequestParamAndValueNotNull(Map<String, Object> params, String... args) throws SuperCodeTraceException {

        if (params.size() == 0) {
            throw new SuperCodeTraceException("业务参数不能为空");
        }

        if (args.length == 0) {
            return;
        }

        List<String> list1 = new ArrayList<>();
        list1.addAll(params.keySet());

        for (String key : list1) {
            if (params.get(key) == null || params.get(key).toString().equals("") || params.get(key).toString().toLowerCase().equals("null")) {
                params.remove(key);
                continue;
            }
            if (params.get(key) instanceof Map) {
                Map map = (Map) params.get(key);
                if (map.size() == 0) {
                    params.remove(key);
                }
            }
        }

        List<String> list = new ArrayList<>();
        for (String arg : args) {
            if (!params.containsKey(arg)) {
                list.add(arg);
            }
        }

        if (list.size() > 0) {
            throw new SuperCodeTraceException("Missing parameter {" + list.toString() + "}");
        }
    }
   /* 
    *//**
     * 功能描述:  获取账户信息
     *
     * @param:
     * @return:
     * @auther: corbett
     * @date: 2018/9/14 15:50
     *//*
    public UserAccount getUser() throws SuperCodeTraceException {
        Object user = request.getSession().getAttribute("user");
        if (user == null) {
            throw new SuperCodeTraceException("您未登入", 401);
        }
        return JSONObject.parseObject(user.toString(), UserAccount.class);
    }

    *//**
     * 功能描述:  获取账户信息
     *
     * @param:
     * @return:
     * @auther: corbett
     * @date: 2018/9/14 15:50
     *//*
    public AccountCache getUserLoginCache() throws SuperCodeTraceException {
        Object user = request.getSession().getAttribute("user");
        if (user == null) {
            throw new SuperCodeTraceException("您未登入", 401);
        }

        return toJavaBean(user, AccountCache.class);
    }


    *//**
     * 功能描述：获取当前登录的组织id
     *
     * @return
     * @Author corbett
     * @Description //TODO
     * @Date 18:27 2018/11/13
     * @Param
     **//*
    public String getOrganizationId() throws SuperCodeTraceException {
        OrganizationCache organizationCache = getUserLoginCache().getOrganizationCache();
        if (organizationCache == null || organizationCache.getOrganizationId() == null) {
            throw new SuperCodeTraceException("请先选择组织", 500);
        }
        return organizationCache.getOrganizationId();
    }

    *//**
     * 功能描述：获取当前登录的组织名
     *
     * @return
     * @Author corbett
     * @Description //TODO
     * @Date 18:27 2018/11/13
     * @Param
     **//*
    public String getOrganizationName() throws SuperCodeTraceException {
        OrganizationCache organizationCache = getUserLoginCache().getOrganizationCache();
        if (organizationCache == null || organizationCache.getOrganizationFullName() == null) {
            throw new SuperCodeTraceException("请先选择组织", 500);
        }
        return organizationCache.getOrganizationFullName();
    }

    *//**
     * 功能描述：获取当前登录的系统id
     *
     * @return
     * @Author corbett
     * @Description //TODO
     * @Date 18:27 2018/11/13
     * @Param
     **//*
    public String getSysId() throws SuperCodeTraceException {
        OrganizationCache organizationCache = getUserLoginCache().getOrganizationCache();
        if (organizationCache == null
                || organizationCache.getOrganizationId() == null) {

            SysCache sysCache = getUserLoginCache().getSysCache();
            if (sysCache != null && sysCache.getSysId() != null) {
                return sysCache.getSysId();
            }
            throw new SuperCodeTraceException("请先选择组织，或者选择系统", 500);
        }
        SysCache sysCache = organizationCache.getSysCache();
        if (sysCache == null || sysCache.getSysId() == null) {
            throw new SuperCodeTraceException("请先选择系统", 500);
        }
        return sysCache.getSysId();
    }

    *//**
     * 功能描述：获取当前登录的系统名
     *
     * @return
     * @Author corbett
     * @Description //TODO
     * @Date 18:27 2018/11/13
     * @Param
     **//*
    public String getSysName() throws SuperCodeTraceException {
        OrganizationCache organizationCache = getUserLoginCache().getOrganizationCache();
        if (organizationCache == null
                || organizationCache.getOrganizationId() == null) {

            SysCache sysCache = getUserLoginCache().getSysCache();
            if (sysCache != null && sysCache.getSysName() != null) {
                return sysCache.getSysName();
            }
            throw new SuperCodeTraceException("请先选择组织,或者选择系统", 500);
        }
        SysCache sysCache = organizationCache.getSysCache();
        if (sysCache == null || sysCache.getSysName() == null) {
            throw new SuperCodeTraceException("请先选择系统", 500);
        }
        return sysCache.getSysName();
    }
    */
    /**
     * 功能描述：对象转换成对应的对象
     *
     * @return T
     * @Author corbett
     * @Description //TODO
     * @Date 14:07 2018/10/12
     * @Param [o-需要转换的对象, c--需要转换成的对象类型]
     **/
    public <T> T toJavaBean(Object o, Class<T> c) throws SuperCodeTraceException {

        if (isNull(o) || isNull(c)) {
            throw new SuperCodeTraceException("转换的对象或者class类都不能为null");
        }
        return JSONObject.parseObject(o instanceof String ? o.toString() : JSON.toJSONString(o), c);
    }
    
	public  String StringFilter(String str) throws PatternSyntaxException {
		// 只允许字母和数字 // String regEx ="[^a-zA-Z0-9]";
		// 清除掉所有特殊字符
		String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？-]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}
	
	/**
	 * 获取动态表已经创建在哪个库
	 * @return
	 */
	public int getDynamicDatabaseSequence() {
		String value=redisUtil.get(TraceBaseConstants.DATABASE1_TABLE_NUM_REDIS_KEY);
		if (StringUtils.isBlank(value)) {
			return 1;
		}
		Long key=Long.parseLong(value);
		if (key<100L) {
			return 1;
		}
		return 2;
	}
	/**
	 * 设置数据库表数量
	 * @param databaseNum
	 * @param newTableNum
	 */
	public void setDynamicDatabaseNum(int databaseNum,long newTableNum) {
		String key=null;
		if (1==databaseNum) {
			key=TraceBaseConstants.DATABASE1_TABLE_NUM_REDIS_KEY;
		}else {
			key=TraceBaseConstants.DATABASE2_TABLE_NUM_REDIS_KEY;
		}
		String value=redisUtil.get(key);
		if (StringUtils.isBlank(value)) {
			redisUtil.set(key,newTableNum+"");
		}else {
			redisUtil.set(key,String.valueOf(Long.parseLong(value)+newTableNum));
		}
	}

}
