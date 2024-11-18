package org.zakaria.whatsappsenderselenium.service;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;
import org.zakaria.whatsappsenderselenium.model.MessageRequest;

import java.time.Duration;

/**
 * Service class responsible for sending WhatsApp messages using Selenium WebDriver.
 */
@Service
public class MessageService {

    // Selenium WebDriver instance for automating browser interactions
    private final WebDriver driver;

    /**
     * Constructs a new {@link MessageService} with the specified {@link WebDriver}.
     *
     * @param driver the Selenium WebDriver to use for browser automation
     */
    public MessageService(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Sends a WhatsApp message based on the provided {@link MessageRequest}.
     *
     * @param request the details of the message to be sent
     * @return a string indicating the result of the operation
     */
    public String sendMessage(MessageRequest request) {
        try {
            // Navigate to WhatsApp Web
            driver.get("https://web.whatsapp.com");
            System.out.println("Navigated to WhatsApp Web.");

            // Initialize explicit wait with a timeout of 120 seconds
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(120));

            // Check if the user is already logged in by looking for the chat pane
            boolean isLoggedIn;
            try {
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("pane-side")));
                isLoggedIn = true;
                System.out.println("Already logged in to WhatsApp Web.");
            } catch (TimeoutException e) {
                isLoggedIn = false;
            }

            if (!isLoggedIn) {
                System.out.println("Waiting for QR code to be scanned...");
                // Wait until the QR code canvas is visible
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//canvas[@aria-label='Scan me!']")));
                System.out.println("QR code detected. Please scan it with your WhatsApp mobile app.");

                // Wait until the chat pane becomes visible after scanning the QR code
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("pane-side")));
                System.out.println("Logged in to WhatsApp Web successfully.");
            }

            if (request.isGroup()) {
                // Navigate to the group chat via the search box
                WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//div[@contenteditable='true'][@data-tab='3']")
                ));
                searchBox.click();
                searchBox.sendKeys(request.getPhoneNumber()); // Enter group name or ID
                searchBox.sendKeys(Keys.RETURN);
                System.out.println("Navigated to group chat: " + request.getPhoneNumber());
            } else {
                // Direct message to an individual phone number
                String url = "https://web.whatsapp.com/send?phone=" + request.getPhoneNumber();
                driver.get(url);
                System.out.println("Navigated to chat for phone number: " + request.getPhoneNumber());
            }

            // Wait for the message input box to be clickable
            WebElement messageBox = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//div[@contenteditable='true'][@data-tab='10']")
            ));
            messageBox.click();

            // Enter the message content and send it
            messageBox.sendKeys(request.getMessage());
            messageBox.sendKeys(Keys.RETURN);

            // Wait until the message box is cleared, indicating the message has been sent
            wait.until(ExpectedConditions.attributeToBe(messageBox, "textContent", ""));
            System.out.println("Message sent successfully and input box cleared.");

            return "Message sent successfully!";
        } catch (Exception e) {
            // Log the exception stack trace for debugging purposes
            e.printStackTrace();
            return "Failed to send message: " + e.getMessage();
        }
    }
}
