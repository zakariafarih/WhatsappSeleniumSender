package org.zakaria.whatsappsenderselenium;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the WhatsappSenderSelenium Spring Boot application.
 * Bootstraps the application and starts the embedded server.
 */
@SpringBootApplication
public class WhatsappSenderSeleniumApplication {

	/**
	 * Main method that launches the Spring Boot application.
	 *
	 * @param args command-line arguments (not used)
	 */
	public static void main(String[] args) {
		SpringApplication.run(WhatsappSenderSeleniumApplication.class, args);
	}
}
