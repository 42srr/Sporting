package kr.mytownsports;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MyTownSportsApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyTownSportsApplication.class, args);
    }
}
