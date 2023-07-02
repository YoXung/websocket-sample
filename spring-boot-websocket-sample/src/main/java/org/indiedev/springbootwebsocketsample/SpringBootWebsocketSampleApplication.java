package org.indiedev.springbootwebsocketsample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling // 开启定时任务支持
public class SpringBootWebsocketSampleApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(SpringBootWebsocketSampleApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringBootWebsocketSampleApplication.class, args);
	}

}
