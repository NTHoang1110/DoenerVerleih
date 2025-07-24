package de.hsrm.mi.web.projekt.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class FrontendNachrichtService {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @EventListener
    public void sendEvent(FrontendNachrichtEvent ev) {
        messagingTemplate.convertAndSend("/topic/doener", ev);
    }
}
