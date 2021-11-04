package com.catdev.project;

import com.catdev.project.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

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
        logger.info("Hello from Log4j 2 - num : {}", () -> "clear Token");
        logger.info("Active Profile : {}", () -> env.getActiveProfiles());
    }
}
