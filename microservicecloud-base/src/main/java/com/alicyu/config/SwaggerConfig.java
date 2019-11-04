package com.alicyu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

  /**
   * 可以定义多个组，比如本类中定义把base和task区分开了 （访问页面就可以看到效果了）
   * 根据basePackage显示不同路径的controller
   */

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder().title("示例项目") // 大标题
        .description("示例项目，包括用户相关操作：登录、登录判断，退出，以及固安app，PC前台后台接口开发") // 详细描述
        .termsOfServiceUrl("") // NO terms of user
        .version("0.0.1") // 版本号
        .build();
  }

  private ApiInfo apiInfo02() {
    return new ApiInfoBuilder().title("Base项目") // 大标题
        .description("Base项目web基础功能") // 详细描述
        .termsOfServiceUrl("") // NO terms of user
        .version("1.0.0") // 版本
        .build();
  }

  /**
   * 可以定义多个组，比如本类中定义把base和task区分开了 （访问页面就可以看到效果了）
   * 
   */
  @Bean
  public Docket createRestApi() {

    return new Docket(DocumentationType.SWAGGER_2) //
        .apiInfo(apiInfo()) //
        .useDefaultResponseMessages(false) //
        .groupName("A-web").select() //
        .apis(RequestHandlerSelectors.basePackage("com.zc.web.controller")) //
        .paths(PathSelectors.any()).build();
  }

  @Bean
  public Docket createRestApi02() {

    return new Docket(DocumentationType.SWAGGER_2) //
        .apiInfo(apiInfo02()) //
        .useDefaultResponseMessages(false) //
        .groupName("Z-base").select() //
        .apis(RequestHandlerSelectors.basePackage("com.zc.base.controller")) //
        .paths(PathSelectors.any()).build();
  }
}