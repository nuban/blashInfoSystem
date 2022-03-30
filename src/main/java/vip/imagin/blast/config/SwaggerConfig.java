package vip.imagin.blast.config;




import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import java.util.ArrayList;
/**
 * @author java1234_小锋
 * @site www.java1234.com
 * @company 南通小锋网络科技有限公司
重启项目，我们发现，APIInfo信息变了；
 * @create 2021-09-21 10:42
 */
@Configuration
public class SwaggerConfig {
    /**
     * 配置swagger的Docket bean
     * @return
     */
    @Bean
    public Docket createRestApi() {
        // 指定swagger3.0版本
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(createApiInfo());
    }
    /**
     * 配置swagger的ApiInfo bean
     * @return
     */
    @Bean
    public ApiInfo createApiInfo(){
        return new ApiInfo("爆炸案情分析系统"
                ,"各个接口的描述"
                ,"3.0"
                ,"http://www.baidu.com/"
                ,new Contact("东子", "http://www.baidu.com",
                "2911890696@qq.com")
                ,"Apache 2.0"
                ,"http://www.apache.org/licenses/LICENSE-2.0"
                ,new ArrayList());
    }
}


//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.env.Environment;
//import org.springframework.core.env.Profiles;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.Contact;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
//import java.util.ArrayList;
//
//@Configuration
//public class SwaggerConfig {
//    //配置了Swagger的docket的Bean实例
//    @Bean
//    public Docket docket(Environment environment) {
//
//        //设置要显示的环境
//        //Profiles profiles = Profiles.of("dev");
//        //获取项目的环境
//        //通过environment.acceptsProfiles方法判断是否是处在自己设定的环境中
//        //boolean flag = environment.acceptsProfiles(profiles);
//
//        return new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(apiInfo())
//                //.enable(flag)
//                .select()
//                //RequestHandlerSelectors  配置要扫描接口的方式
//                //basePackage   指定要扫描的包
//                //any()扫描全部
//                //none()不扫描
//                //withClassAnnotation() 扫描类上的注解
//                //withMethodAnnotation() 扫描方法上的注解
//                .apis(RequestHandlerSelectors.basePackage("vip.imagin.blast.controller"))
//                //path（）过滤什么路径
//                //.paths(PathSelectors.any())
//
//                .build();
//
//    }
//
//    private ApiInfo apiInfo() {
//        //作者信息
//        Contact contact = new Contact("欧神诺", "http://114.55.254.24:8090/", "2784247628@qq.com");
//        return new ApiInfo("Swagger Api 接口",
//                "描述",
//                "v1.0", "urn:tos",
//                contact,
//                "Apache 2.0",
//                "http://www.apache.org/licenses/LICENSE-2.0",
//                new ArrayList());
//
//    }
//}
