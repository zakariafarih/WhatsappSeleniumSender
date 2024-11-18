package org.zakaria.whatsappsenderselenium.controller;

import org.zakaria.whatsappsenderselenium.model.MessageRequest;
import org.zakaria.whatsappsenderselenium.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for handling message-related endpoints.
 * Provides an endpoint to send WhatsApp messages.
 */
@RestController
@RequestMapping("/api/messages")
public class MessageController {

    // Service layer dependency for message operations
    private final MessageService messageService;

    /**
     * Constructs a new {@link MessageController} with the specified {@link MessageService}.
     *
     * @param messageService the service to handle message sending logic
     */
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    /**
     * Sends a WhatsApp message to a specified phone number or group.
     *
     * @param request the {@link MessageRequest} containing message details
     * @return a {@link ResponseEntity} with the result of the message sending operation
     */
    @Operation(
            summary = "Send a WhatsApp message",
            description = "Sends a message to a specified phone number or group."
    )
    @ApiResponse(responseCode = "200", description = "Message sent successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request parameters")
    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestBody MessageRequest request) {
        // Validate the presence of the phone number
        if (request.getPhoneNumber() == null || request.getPhoneNumber().isEmpty()) {
            return ResponseEntity.badRequest().body("Phone number is required.");
        }

        // Validate the presence of the message content
        if (request.getMessage() == null || request.getMessage().isEmpty()) {
            return ResponseEntity.badRequest().body("Message content is required.");
        }

        // Delegate the message sending logic to the service layer
        String response = messageService.sendMessage(request);

        return ResponseEntity.ok(response);
    }
}
