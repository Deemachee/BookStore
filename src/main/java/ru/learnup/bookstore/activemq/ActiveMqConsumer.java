package ru.learnup.bookstore.activemq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ActiveMqConsumer {

    @JmsListener(destination = "report")
    public void processMessages(String message) {
        log.info("Received message : " + message);
    }
}
