package com.jgw.supercodeplatform.redisTest;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jgw.supercodeplatform.SuperCodeTraceApplication;
import com.jgw.supercodeplatform.trace.config.redis.RedisUtil;

@RunWith(SpringJUnit4ClassRunner.class) // SpringJUnit支持，由此引入Spring-Test框架支持！
@SpringBootTest(classes = SuperCodeTraceApplication.class) // 指定我们SpringBoot工程的Application启动类
public class RedisTest {

	@Autowired
	private RedisUtil redisUtil;
	

}
