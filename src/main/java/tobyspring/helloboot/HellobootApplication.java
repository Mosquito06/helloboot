package tobyspring.helloboot;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;


import javax.annotation.PostConstruct;

// @SpringBootApplication
@SpringBootApplication
public class HellobootApplication
{


    private final JdbcTemplate jdbcTemplate;

    public HellobootApplication(JdbcTemplate jdbcTemplate)
    {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 생성된 이후 실행 되는 애노테이션
    @PostConstruct
    void init()
    {
        jdbcTemplate.execute("create table if not exists hello(name varchar(50) primary key, count int)");
    }

    public static void main(String[] args)
    {
        SpringApplication.run(HellobootApplication.class, args);
    }
}
