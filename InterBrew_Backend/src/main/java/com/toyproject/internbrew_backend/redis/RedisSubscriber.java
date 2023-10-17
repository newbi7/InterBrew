package com.toyproject.internbrew_backend.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisSubscriber {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void handleMessage(String message) {
        messagingTemplate.convertAndSend("/topic/alarm", message);
    }
}
