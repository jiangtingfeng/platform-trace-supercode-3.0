//package com.jgw.supercodeplatform.trace.config.redis;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import redis.clients.jedis.JedisPoolConfig;
//
//@Configuration("redisConfigInDataBase10ForUserCenter")
//public class RedisConfigInDataBase10ForUserCenter {
//
//    // Redis服务器地址
//    @Value("${redis.host10}")
//    private String host;
//
//    // Redis服务器连接端口
//    @Value("${redis.port10}")
//    private int port;
//    // Redis服务器连接密码（默认为空）
//    @Value("${redis.password10}")
//    private String password;
//    // 连接超时时间（毫秒）
//    @Value("${redis.timeout10}")
//    private int timeout;
//    // 连接超时时间（毫秒）
//    @Value("${redis.database10}")
//    private int database;
//    // 连接池最大连接数（使用负值表示没有限制）
//    @Value("${redis.pool10.max-active}")
//    private int maxTotal;
//    // 连接池最大阻塞等待时间（使用负值表示没有限制）
//    @Value("${redis.pool10.max-wait}")
//    private int maxWaitMillis;
//    // 连接池中的最大空闲连接
//    @Value("${redis.pool10.max-idle}")
//    private int maxIdle;
//    // 连接池中的最小空闲连接
//    @Value("${redis.pool10.min-idle}")
//    private int minIdle;
//
//    public JedisPoolConfig jedisPoolConfig10() {
//        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
//        // 连接池最大连接数（使用负值表示没有限制）
//        jedisPoolConfig.setMaxTotal(this.maxTotal);
//        // 连接池最大阻塞等待时间（使用负值表示没有限制）
//        jedisPoolConfig.setMaxWaitMillis(this.maxWaitMillis);
//        // 连接池中的最大空闲连接
//        jedisPoolConfig.setMaxIdle(this.maxIdle);
//        // 连接池中的最小空闲连接
//        jedisPoolConfig.setMinIdle(this.minIdle);
////        jedisPoolConfig.setTestOnBorrow(true);
////        jedisPoolConfig.setTestOnCreate(true);
////        jedisPoolConfig.setTestWhileIdle(true);
//        return jedisPoolConfig;
//    }
//
//    public RedisConnectionFactory jedisConnectionFactory10() {
//        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(jedisPoolConfig10());
//        jedisConnectionFactory.setHostName(this.host);
//        jedisConnectionFactory.setPort(this.port);
//        jedisConnectionFactory.setPassword(this.password);//密码
//        jedisConnectionFactory.setDatabase(this.database);
//        return jedisConnectionFactory;
//    }
//
//	@Bean(name = "stringRedisTemplate10")
//	public StringRedisTemplate stringRedisTemplate10() {
//		StringRedisTemplate template = new StringRedisTemplate(jedisConnectionFactory10());
//		return template;
//	}
//
//
//
//}
//
//
//
