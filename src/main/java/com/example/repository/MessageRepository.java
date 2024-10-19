package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer>{
    
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("delete Message m where m.messageId = :messageId")
    int deletMessageById(@Param("messageId") Integer messageId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update Message m set m.messageText = :messageText where m.messageId = :messageId")
    int updateMessageById(@Param("messageText") String messageText, @Param("messageId") Integer messageId);

    @Query("select m FROM Message m WHERE m.postedBy = :accountId")
    List<Message> findAllMessagesByAccountId(@Param("accountId") Integer accountId);

}
