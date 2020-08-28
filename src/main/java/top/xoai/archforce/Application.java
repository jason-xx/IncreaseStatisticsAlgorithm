package top.xoai.archforce;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author wx
 * @version 1.0
 * @date 2020/8/14 4:55 下午
 */
@SpringBootApplication
@MapperScan("top.xoai.archforce")
@EnableScheduling
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
