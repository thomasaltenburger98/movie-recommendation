package com.movierecommendation.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

@SpringBootApplication
@EnableScheduling
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

}

@Component
class PortListener implements ApplicationListener<ContextRefreshedEvent> {
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		// Get the port of the running application

		int port = -1;
		try {
			port = event.getApplicationContext().getEnvironment().getProperty("local.server.port", Integer.class);
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

		if (port == -1) {
			System.out.println("Could not get the port of the running application");
			return;
		}
		System.out.println("Application is running on port: " + port);
	}
}
