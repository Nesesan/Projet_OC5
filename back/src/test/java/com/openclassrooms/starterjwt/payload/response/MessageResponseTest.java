package com.openclassrooms.starterjwt.payload.response;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MessageResponseTest {
    @Test
    void testConstructorAndGetMessage() {

        String expectedMessage = "Operation successful";

        MessageResponse response = new MessageResponse(expectedMessage);

        assertEquals(expectedMessage, response.getMessage());
    }

    @Test
    void testSetMessage() {

        MessageResponse response = new MessageResponse("Initial message");


        String newMessage = "Updated message";
        response.setMessage(newMessage);


        assertEquals(newMessage, response.getMessage());
    }
}
