package com.kalinowski.talktalk.controller;

import com.kalinowski.talktalk.dto.ConversationBean;
import com.kalinowski.talktalk.dto.MessageHolder;
import com.kalinowski.talktalk.model.Conversation;
import com.kalinowski.talktalk.model.Message;
import com.kalinowski.talktalk.model.User;
import com.kalinowski.talktalk.service.ConversationService;
import com.kalinowski.talktalk.util.ConversationTranslator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.Collection;
import java.util.List;

@Slf4j
@Controller
public class ConversationController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private ConversationService conversationService;

    /**
     * Receives message from STOMP Client and persist it to a database.
     * MessageMapping annotation reflects destination of SEND frame.
     * SendToUser annotation implies that return value will be sent back to a sender.
     * SimpMessagingTemplate is used to send the message to a recipient.
     */
    @MessageMapping("/conversationController")
    @SendToUser("/queue/private-messaging")
    public MessageHolder processPrivateMessage(@Payload MessageHolder messageHolder, Principal principal) {
        Integer conversationId = messageHolder.getConversationId();
        String recipientName = messageHolder.getRecipientsString(); //TODO: Single recipient for now
        String destination = "/queue/private-chat";

        conversationService.saveMessage(messageHolder);

        //TODO: there will be separate implementation for group conversation - for now, it will just get the only participant
        simpMessagingTemplate.convertAndSendToUser(recipientName, destination, messageHolder);

        log.info("[sendPrivateMessage] sender:{}, recipient: {}, messageContent: {}",
                principal.getName(), recipientName, messageHolder.getContent());

        return messageHolder;
    }

    @GetMapping("/conversations")
    public @ResponseBody Collection<ConversationBean> getUserConversations(Principal principal) {
        Collection<Conversation> conversations = conversationService.findAll(principal);
        ConversationTranslator translator = new ConversationTranslator(principal);
        return translator.getBeans(conversations);
    }

    @GetMapping("/conversations/{id}")
    public @ResponseBody Conversation getConversation(@PathVariable int id) {
        return conversationService.find(id);
    }

    @GetMapping("/conversations/{id}/participants")
    public @ResponseBody Collection<User> getConversationParticipants(@PathVariable int id) {
        Conversation conversation = conversationService.find(id);
        return conversation.getParticipants();
    }

    @GetMapping("/conversations/{id}/messages")
    public @ResponseBody Collection<Message> getConversationMessages(@PathVariable int id) {
        Conversation conversation = conversationService.find(id);
        return conversation.getMessageList();
    }

    @GetMapping("/conversations/{id}/messages/last")
    public @ResponseBody Message getLastMessage(@PathVariable int id) {
        Conversation conversation = conversationService.find(id);
        List<Message> messageList = conversation.getMessageList();
        return messageList.get(messageList.size() - 1);
    }

}
