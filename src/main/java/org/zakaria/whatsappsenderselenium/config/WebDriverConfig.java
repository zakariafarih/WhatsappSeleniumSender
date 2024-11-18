package org.zakaria.whatsappsenderselenium.config;

import io.github.bonigarcia.wdm.WebDriverManager;
import jakarta.annotation.PreDestroy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

/**
 * Configuration class for Selenium WebDriver.
 * Sets up ChromeDriver with specified options and manages the user data directory.
 */
@Configuration
public class WebDriverConfig {

    // Indicates whether Chrome should run in headless mode
    @Value("${selenium.headless}")
    private boolean headless;

    // Directory for storing user data (e.g., session information)
    @Value("${selenium.user-data-dir}")
    private String userDataDir;

    // Chrome profile directory to use, defaults to 'Default' if not specified
    @Value("${selenium.profile-directory:Default}")
    private String profileDirectory;

    // WebDriver instance managed by this configuration
    private WebDriver driver;

    /**
     * Configures and provides the Selenium WebDriver bean.
     *
     * @return a configured instance of {@link WebDriver}
     */
    @Bean
    public WebDriver webDriver() {
        // Ensure the user data directory exists and is writable
        ensureUserDataDirectory();

        // Set up ChromeDriver using WebDriverManager
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();

        // Configure headless mode if enabled
        if (headless) {
            options.addArguments("--headless");
        }

        // Disable GPU acceleration (useful for Windows environments)
        options.addArguments("--disable-gpu");

        // Set window size for consistent behavior
        options.addArguments("--window-size=1920,1080");

        // Specify the user data directory and profile
        options.addArguments("user-data-dir=" + userDataDir);
        options.addArguments("profile-directory=" + profileDirectory);

        // Start Chrome maximized
        options.addArguments("start-maximized");

        // Initialize the ChromeDriver with the configured options
        driver = new ChromeDriver(options);

        return driver;
    }

    /**
     * Ensures that the user data directory exists and is writable.
     * Creates the directory if it does not exist.
     *
     * @throws RuntimeException if the directory cannot be created or is not writable
     */
    private void ensureUserDataDirectory() {
        File sessionDir = new File(userDataDir);

        if (!sessionDir.exists()) {
            System.out.println("User data directory does not exist. Creating: " + userDataDir);
            if (!sessionDir.mkdirs()) {
                throw new RuntimeException("Failed to create the user data directory: " + userDataDir);
            }
        } else if (!sessionDir.canWrite()) {
            throw new RuntimeException("Cannot write to the user data directory: " + userDataDir);
        }
    }

    /**
     * Cleans up the WebDriver instance upon application shutdown.
     * Ensures that the browser is closed and resources are freed.
     */
    @PreDestroy
    public void cleanUp() {
        if (driver != null) {
            driver.quit();
            System.out.println("WebDriver instance closed.");
        }
    }
}
