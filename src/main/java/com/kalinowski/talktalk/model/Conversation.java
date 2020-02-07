package com.kalinowski.talktalk.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kalinowski.talktalk.exception.ConversationMessagesException;
import com.kalinowski.talktalk.exception.ConversationParticipantsException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "conversation")
public class Conversation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private Timestamp firstMessageTimestamp;
    private Timestamp lastMessageTimestamp;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_conversation_link",
            joinColumns = @JoinColumn(name = "conversation_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> participants;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "conversation", fetch = FetchType.EAGER)
    private List<Message> messageList;

    public Conversation() {
    }

    public Conversation(Set<User> participants) {
        if (participants == null || participants.isEmpty()) {
            throw new ConversationParticipantsException("Cannot create a conversation without participants");
        }
        if (messageList == null || messageList.isEmpty()) {
            throw new ConversationMessagesException("Cannot create a conversation without messages");
        }
        setDefaultName();
        setFirstMessageTimestamp();
        setLastMessageTimestamp();
        this.participants = participants;
        this.messageList = new LinkedList<>();
    }

    public void addMessage(Message message) {
        messageList.add(message);
        setLastMessageTimestamp();
    }

    public Message getLastMessage() {
        if (messageList == null || messageList.isEmpty()) {
            return null;
        } else {
            int last = messageList.size() - 1;
            return messageList.get(last);
        }
    }

    private void setFirstMessageTimestamp() {
        int first = 0;
        Message firstMessage = messageList.get(first);

        if (firstMessage != null) {
            this.lastMessageTimestamp = firstMessage.getCreatedTimestamp();
        }
    }

    private void setLastMessageTimestamp() {
        Message lastMessage = messageList.get(messageList.size() - 1);

        if (lastMessage != null) {
            this.lastMessageTimestamp = lastMessage.getCreatedTimestamp();
        }
    }

    private void setDefaultName() {
        int first = 0;
        Message firstMessage = messageList.get(first);
        User conversationStarter = firstMessage.getSender();

        StringBuilder nameBuilder = new StringBuilder();
        for (User participant : participants) {
            if (!participant.getUserName().equals(conversationStarter.getUserName())) {
                nameBuilder.append(participant.getUserName());
                nameBuilder.append(", ");
            }
        }
        nameBuilder.replace(nameBuilder.length() - 2, nameBuilder.length() - 1, "");

        this.name = nameBuilder.toString();
    }

}
