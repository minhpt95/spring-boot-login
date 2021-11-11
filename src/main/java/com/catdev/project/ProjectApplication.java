package com.catdev.project;

import com.catdev.project.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.TimeZone;

@SpringBootApplication
@EnableSwagger2
@EnableScheduling
@EnableAsync
public class ProjectApplication {

    private static final Logger logger = LogManager.getLogger(ProjectApplication.class);

    @Autowired
    private UserService userService;

    @Autowired
    private Environment env;

    public static void main(String[] args) {
        SpringApplication.run(ProjectApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void clearToken(){
        userService.clearAllToken();
        logger.info("Clear Token After Start Application : {}", () -> "clear Token");
    }

    @EventListener(ApplicationReadyEvent.class)
    public void setApplicationTimeZone(){
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        logger.info("TimeZone : {} , Instant : {} , Timestamp : {}", TimeZone::getDefault, Instant::now,() -> Timestamp.from(Instant.now()));
    }
}
