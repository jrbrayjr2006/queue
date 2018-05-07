package com.flood.cloud.queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import javax.sql.DataSource;

@SpringBootApplication
@EnableAutoConfiguration
public class QueueApplication {


	public static void main(String[] args) {
		SpringApplication.run(QueueApplication.class, args);
	}
}
