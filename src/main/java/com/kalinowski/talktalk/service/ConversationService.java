package com.kalinowski.talktalk.service;

import com.kalinowski.talktalk.dto.MessageHolder;
import com.kalinowski.talktalk.model.Conversation;

import java.security.Principal;
import java.util.Collection;

public interface ConversationService {
    Conversation saveMessage(MessageHolder messageHolder);

    Collection<Conversation> findAll(Principal principal);

    Conversation find(int id);
}
