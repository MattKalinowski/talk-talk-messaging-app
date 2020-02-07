package com.kalinowski.talktalk.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "message")
public class Message implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    private User sender;

    @JsonIgnore
    @ManyToOne
    private Conversation conversation;

    @Lob
    private String content;

    private Timestamp createdTimestamp;

    public Message() {
    }

    public Message(User sender, Conversation conversation, String content) {
        this.sender = sender;
        this.conversation = conversation;
        this.content = content;
        this.createdTimestamp = new Timestamp(System.currentTimeMillis());
        conversation.addMessage(this);
    }

}
