package com.jgw.supercodeplatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableEurekaClient
@EnableAsync//允许异步
@EnableTransactionManagement
public class SuperCodeTraceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SuperCodeTraceApplication.class, args);
    }

//    @Configuration
//    static class MyWebMvcConfig implements WebMvcConfigurer{
//        @Override
//        public void addInterceptors(InterceptorRegistry registry) {
//            registry.addInterceptor(new UserSessionInterceptor()).addPathPatterns("/**")
//                    .excludePathPatterns(excludePathPatterns());
//        }
//        private static String[] excludePathPatterns() {
//            return new String[]{
//                    //swagger
//                    "/swagger-resources/configuration/ui",
//                    "/swagger-resources",
//                    "/swagger-resources/configuration/security",
//                    "/swagger-ui.html",
//                    "/v2/**",
//                    "/doc.html",
//                    //静态资源
//                    "/webjars/**"
//            };
//        }
//    }
}
