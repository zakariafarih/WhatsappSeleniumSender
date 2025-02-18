package org.zakaria.whatsappsenderselenium.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

/**
 * Model class representing the request payload for sending a WhatsApp message.
 */
@Schema(description = "Request payload for sending a WhatsApp message")
public class MessageRequest {

    /**
     * Recipient's phone number or group ID.
     * -- GETTER --
     *  Gets the recipient's phone number or group ID.
     *
     * @return the phone number or group ID

     */
    @Getter
    @Schema(description = "Recipient's phone number or group ID", example = "DAMwon Kia")
    @NotEmpty(message = "Phone number is required.")
    private String phoneNumber;

    /**
     * Message content to be sent.
     * -- SETTER --
     *  Sets the message content to be sent.
     *
     *
     * -- GETTER --
     *  Gets the message content to be sent.
     *
     @param message the message content to set
      * @return the message content

     */
    @Getter
    @Setter
    @Schema(description = "Message content to be sent", example = "Hello, this is a test message!")
    @NotEmpty(message = "Message content is required.")
    private String message;

    /**
     * Flag indicating if the recipient is a group.
     */
    @Schema(description = "Flag indicating if the recipient is a group", example = "true")
    private boolean isGroup;

    /**
     * Default constructor required for deserialization.
     */
    public MessageRequest() {}

    /**
     * Constructs a new {@link MessageRequest} with the specified details.
     *
     * @param phoneNumber the recipient's phone number or group ID
     * @param message     the message content
     * @param isGroup     flag indicating if the recipient is a group
     */
    public MessageRequest(String phoneNumber, String message, boolean isGroup) {
        this.phoneNumber = phoneNumber;
        this.message = message;
        this.isGroup = isGroup;
    }

    /**
     * Checks if the recipient is a group.
     *
     * @return {@code true} if the recipient is a group, {@code false} otherwise
     */
    public boolean isGroup() {
        return isGroup;
    }

    /**
     * Sets the flag indicating if the recipient is a group.
     *
     * @param isGroup {@code true} if the recipient is a group, {@code false} otherwise
     */
    public void setIsGroup(boolean isGroup) {
        this.isGroup = isGroup;
    }
}
