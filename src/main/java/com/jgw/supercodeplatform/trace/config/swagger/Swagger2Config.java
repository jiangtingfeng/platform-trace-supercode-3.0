package com.jgw.supercodeplatform.trace.config.swagger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

 /**
   * 功能描述：Swagger2Config 配置功能模块
   * @Author corbett
   * @Description //TODO
   * @Date 15:37 2018/10/19
   **/
@Configuration
@EnableSwagger2
public class Swagger2Config {
    @Value("${swagger2.enable}") private boolean enable;

     @Bean("模板管理")
     public Docket sysApis() {
         return new Docket(DocumentationType.SWAGGER_2)
                 .groupName("模板管理")
                 .select()
                 .apis(RequestHandlerSelectors.basePackage("com.jgw.supercodeplatform.trace.controller.template"))
                 .paths(PathSelectors.any())
                 .build()
                 .apiInfo(apiInfo())
                 .enable(enable);
     }
     @Bean("区块链模块")
     public Docket blockchainApis() {
         return new Docket(DocumentationType.SWAGGER_2)
                 .groupName("溯源区块链")
                 .select()
                 .apis(RequestHandlerSelectors.basePackage("com.jgw.supercodeplatform.trace.controller.blockchain"))
                 .paths(PathSelectors.any())
                 .build()
                 .apiInfo(apiInfo())
                 .enable(enable);
     }
     @Bean("蚂蚁金服区块链模块")
     public Docket antchainApis() {
         return new Docket(DocumentationType.SWAGGER_2)
                 .groupName("蚂蚁金服溯源区块链")
                 .select()
                 .apis(RequestHandlerSelectors.basePackage("com.jgw.supercodeplatform.trace.controller.antchain"))
                 .paths(PathSelectors.any())
                 .build()
                 .apiInfo(apiInfo())
                 .enable(enable);
     }
     @Bean("动态表增删改查")
     public Docket dynamicApis() {
         return new Docket(DocumentationType.SWAGGER_2)
                 .groupName("动态表增删改查")
                 .select()
                 .apis(RequestHandlerSelectors.basePackage("com.jgw.supercodeplatform.trace.controller.dynamic"))
                 .paths(PathSelectors.any())
                 .build()
                 .apiInfo(apiInfo())
                 .enable(enable);
     }

     @Bean("批次管理")
     public Docket batchApis() {
         return new Docket(DocumentationType.SWAGGER_2)
                 .groupName("批次管理")
                 .select()
                 .apis(RequestHandlerSelectors.basePackage("com.jgw.supercodeplatform.trace.controller.tracebatch"))
                 .paths(PathSelectors.any())
                 .build()
                 .apiInfo(apiInfo())
                 .enable(enable);
     }

     @Bean("产品检测")
     public Docket testingTypeApis() {
         return new Docket(DocumentationType.SWAGGER_2)
                 .groupName("产品检测")
                 .select()
                 .apis(RequestHandlerSelectors.basePackage("com.jgw.supercodeplatform.trace.controller.producttesting"))
                 .paths(PathSelectors.any())
                 .build()
                 .apiInfo(apiInfo())
                 .enable(enable);
     }

     @Bean("码关联")
     public Docket codeApis() {
         return new Docket(DocumentationType.SWAGGER_2)
                 .groupName("码关联")
                 .select()
                 .apis(RequestHandlerSelectors.basePackage("com.jgw.supercodeplatform.trace.controller.code"))
                 .paths(PathSelectors.any())
                 .build()
                 .apiInfo(apiInfo())
                 .enable(enable);
     }


     @Bean("地块生产信息")
     public Docket massifApis() {
         return new Docket(DocumentationType.SWAGGER_2)
                 .groupName("地块生产信息")
                 .select()
                 .apis(RequestHandlerSelectors.basePackage("com.jgw.supercodeplatform.trace.controller.massifbatch"))
                 .paths(PathSelectors.any())
                 .build()
                 .apiInfo(apiInfo())
                 .enable(enable);
     }

     @Bean("溯源公共接口")
     public Docket test() {
         return new Docket(DocumentationType.SWAGGER_2)
                 .groupName("工具接口")
                 .select()
                 .apis(RequestHandlerSelectors.basePackage("com.jgw.supercodeplatform.trace.controller.common"))
                 .paths(PathSelectors.any())
                 .build()
                 .apiInfo(apiInfo())
                 .enable(enable);
     }

     @Bean("枣阳桃溯源")
     public Docket zaoyangPeachApis() {
         return new Docket(DocumentationType.SWAGGER_2)
                 .groupName("枣阳桃溯源")
                 .select()
                 .apis(RequestHandlerSelectors.basePackage("com.jgw.supercodeplatform.project.zaoyangpeach.controller"))
                 .paths(PathSelectors.any())
                 .build()
                 .apiInfo(apiInfo())
                 .enable(enable);
     }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("新超级码系统平台接口文档")
                .description("")
                .termsOfServiceUrl("")
                .version("1.0")
                .build();
    }
}