package com.walkingtalking.gunilda;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class GunilDaApplication {

    @Value("${spring.time-zone}")
    private String timeZone;

    @PostConstruct
    public void started() {
        TimeZone.setDefault(TimeZone.getTimeZone(timeZone));
    }

    public static void main(String[] args) {
        SpringApplication.run(GunilDaApplication.class, args);
    }

}
