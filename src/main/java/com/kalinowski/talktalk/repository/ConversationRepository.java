package com.kalinowski.talktalk.repository;

import com.kalinowski.talktalk.model.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversationRepository extends JpaRepository<Conversation, Integer> {
}
