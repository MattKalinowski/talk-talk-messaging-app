package com.kalinowski.talktalk.util;

import com.kalinowski.talktalk.dto.ConversationBean;
import com.kalinowski.talktalk.model.Conversation;
import com.kalinowski.talktalk.model.Message;
import com.kalinowski.talktalk.model.User;

import java.security.Principal;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class ConversationTranslator {

    private Principal principal;

    public ConversationTranslator(Principal principal) {
        this.principal = principal;
    }

    public Collection<ConversationBean> getBeans(Collection<Conversation> conversations) {
        List<ConversationBean> conversationBeans = new LinkedList<>();
        for (Conversation conversation : conversations) {
            ConversationBean bean = getBean(conversation);
            conversationBeans.add(bean);
        }
        return conversationBeans;
    }

    private ConversationBean getBean(Conversation conversation) {
        ConversationBean bean = new ConversationBean();
        bean.setId(Integer.toString(conversation.getId()));
        bean.setName(conversation.getName());
        bean.setPictureURL(getPictureURL(conversation));
        bean.setStatus("online"); //TODO: remove hardcoded value
        String messageContent = "";
        if (conversation.getLastMessage() != null) {
            messageContent = conversation.getLastMessage().getContent();
        }
        bean.setLastMessage(messageContent);
        bean.setLastMessageTime(conversation.getLastMessageTimestamp().toString());
        return bean;
    }

    private String getPictureURL(Conversation conversation) {
        Set<User> participants = conversation.getParticipants();
        for (User user : participants) {
            if (!user.getUserName().equals(principal.getName())) {
                return user.getProfilePictureUrl();
            }
        }
        //TODO: custom creation of group picture
        return "";
    }
}
