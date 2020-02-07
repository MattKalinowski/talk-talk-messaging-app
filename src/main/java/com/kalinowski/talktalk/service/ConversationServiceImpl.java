package com.kalinowski.talktalk.service;

import com.kalinowski.talktalk.dto.MessageHolder;
import com.kalinowski.talktalk.exception.NoSuchConversationException;
import com.kalinowski.talktalk.model.Conversation;
import com.kalinowski.talktalk.model.Message;
import com.kalinowski.talktalk.model.User;
import com.kalinowski.talktalk.repository.ConversationRepository;
import com.kalinowski.talktalk.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ConversationServiceImpl implements ConversationService {

    private ConversationRepository conversationRepository;
    private MessageRepository messageRepository;
    private UserService userService;

    public ConversationServiceImpl(ConversationRepository conversationRepository, MessageRepository messageRepository,
                                   UserService userService) {
        this.conversationRepository = conversationRepository;
        this.messageRepository = messageRepository;
        this.userService = userService;
    }

    @Override
    public Conversation saveMessage(MessageHolder messageHolder) {
        User sender = userService.findUser(messageHolder.getSenderName());
        String content = messageHolder.getContent();

        Optional<Conversation> conversationOptional = conversationRepository.findById(messageHolder.getConversationId());
        Conversation conversation;
        if (conversationOptional.isPresent()) {
            conversation = conversationOptional.get();
        } else {
            Set<User> participants = getAllParticipants(sender, getRecipients(messageHolder.getRecipientsString()));
            conversation = new Conversation(participants);
        }
        Message message = new Message(sender, conversation, content);

        messageRepository.save(message);
        return conversationRepository.save(conversation);
    }

    @Override
    public Collection <Conversation> findAll(Principal principal) {
        User currentUser = userService.findUser(principal.getName());
        Set<Conversation> conversationsSet = currentUser.getConversations();
        List<Conversation> conversations = new ArrayList<>(conversationsSet);
        Collections.sort(conversations, Comparator.comparing(Conversation::getLastMessageTimestamp).reversed());
        return conversations;
    }

    @Override
    public Conversation find(int id) {
        Optional<Conversation> conversation = conversationRepository.findById(id);
        if (conversation.isPresent()) {
            return conversation.get();
        } else {
            throw new NoSuchConversationException("Could not find conversation for ID: " + id);
        }
    }

    private Set<User> getAllParticipants(User sender, Set<User> recipients) {
        Set<User> participants = new LinkedHashSet<>();
        participants.add(sender);
        participants.addAll(recipients);
        return participants;
    }

    private Set<User> getRecipients(String recipientNamesString) {
        Set<User> recipients = new HashSet<>();
        String[] recipientNames = recipientNamesString.split(", ");
        for (String name : recipientNames) {
            User user = userService.findUser(name);
            if (user != null) {
                recipients.add(user);
            }
        }
        return recipients;
    }
}
