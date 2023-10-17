package com.toyproject.internbrew_backend.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisPublisher {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /*
    기본 publish metohd
     */
    public void publish(String message) { stringRedisTemplate.convertAndSend("/topic/alarm", message); }

    /*
    건의사항 추가 실시간 알람 method
    sender: 건의사항 작성자
    message: 출력 메시지
    channel: 알림전달 채널
    */
    public void sendInquiryAlarm(String sender, String channel) {
        stringRedisTemplate.convertAndSend(channel, sender + "님의 건의사항이 도착했습니다.");
    }
}
