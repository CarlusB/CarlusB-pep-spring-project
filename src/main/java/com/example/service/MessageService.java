package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

@Service
@Transactional
public class MessageService {
    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountService accountService){
        this.messageRepository = messageRepository;
    }

    public Message saveMessage(Message message){
        return messageRepository.save(message);
    }

    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }

    public Message getMessageById(Integer messageId){
        Optional<Message> optionalMessage = messageRepository.findById(messageId);
        
        if(optionalMessage.isPresent()){
            return optionalMessage.get();
        }else{
            return null;
        }
    }

    public Optional<Integer> deletMessage(Integer messageId){
        int rowUpdated = messageRepository.deletMessageById(messageId);

        if(rowUpdated > 0){
            return Optional.of(rowUpdated);
        }else{
            return Optional.empty();
        }
    }

    public int updateMessage(String messageText, Integer messageId){
        return messageRepository.updateMessageById(messageText, messageId);
    }

    public List<Message> findAllMessagesByAccountId(Integer accountId){
        return messageRepository.findAllMessagesByAccountId(accountId);
    }
}
