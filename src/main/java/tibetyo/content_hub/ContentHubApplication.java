package tibetyo.content_hub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ContentHubApplication {

    public static void main(String[] args) {
        SpringApplication.run(ContentHubApplication.class, args);
    }

}
