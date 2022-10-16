package com.registration.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableSwagger2
public class Swagger2 {
    /**
     * 创建API应用
     * apiInfo() 增加API相关信息
     * 通过select()函数返回一个ApiSelectorBuilder实例,用来控制哪些接口暴露给Swagger来展现，
     * 本例采用指定扫描的包路径来定义指定要建立API的目录。
     *
     * @return
     */
    @Bean
    public Docket createRestApi() {
        //添加全局响应状态码
        List<ResponseMessage> responseMessageList = new ArrayList<>();
        Arrays.stream(ErrorEnum.values()).forEach(errorEnum -> {
            responseMessageList.add(
                    new ResponseMessageBuilder().code(errorEnum.getCode()).message(errorEnum.getMsg()).build()
            );
        });
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.registration"))
                .paths(PathSelectors.any())
                .build()
                .directModelSubstitute(LocalDateTime.class, String.class)
                .globalResponseMessage(RequestMethod.GET, responseMessageList)
                .globalResponseMessage(RequestMethod.POST, responseMessageList)
                .globalResponseMessage(RequestMethod.PUT, responseMessageList)
                .globalResponseMessage(RequestMethod.DELETE, responseMessageList);
    }

    /**
     * 创建该API的基本信息（这些基本信息会展现在文档页面中）
     * 访问地址：http://项目实际地址/swagger-ui.html
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Registation服务端API文档")
//                .description("作者：")
                .version("1.0")
                .build();
    }

    public enum ErrorEnum {

        /*
         * 错误信息
         * */
        E_200(200, "请求成功"),
        E_201(201,"创建成功"),
        E_202(204,"删除成功"),
        E_400(400,"请求地址不存在或者参数不匹配"),
        E_401(401,"未授权"),
        E_403(403,"被禁止访问"),
        E_404(404,"请求的资源不存在"),
        E_422(422,"创建对象时，发生验证错误"),
        E_500(500,"内部错误"),
        ;

        private Integer Code;
        private String Msg;
        ErrorEnum(Integer Code, String Msg) {
            this.Code = Code;
            this.Msg = Msg;
        }
        public Integer getCode() {
            return Code;
        }
        public String getMsg() {
            return Msg;
        }
    }
}