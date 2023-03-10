package vip.imagin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.oas.annotations.EnableOpenApi;

/**
 * @author 东子
 */
@SpringBootApplication
@EnableOpenApi      //开启Swagger
public class AppApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppApplication.class, args);
    }

}
