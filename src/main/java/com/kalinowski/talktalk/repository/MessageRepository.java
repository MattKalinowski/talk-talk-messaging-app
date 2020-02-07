package com.kalinowski.talktalk.repository;

import com.kalinowski.talktalk.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Integer> {
}
