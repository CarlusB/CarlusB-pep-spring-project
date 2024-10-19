package com.example.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

@RestController
@RequestMapping
public class SocialMediaController {

    private AccountService accountService;
    private MessageService messageService;

    public SocialMediaController(AccountService accountService, MessageService messageService){
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @PostMapping("/register")
    public ResponseEntity<Account> saveAccount(@RequestBody Account newAccount){
        if(newAccount.getUsername().isBlank() || newAccount.getPassword().length() < 4){
            return ResponseEntity.status(400).body(null);
        }

        Account tempAccount = accountService.findAccountByUsername(newAccount.getUsername());

        if(tempAccount != null){
            return ResponseEntity.status(409).body(null);
        }

        tempAccount = accountService.saveAccount(newAccount);
        return ResponseEntity.ok(tempAccount);
    }

    @PostMapping("/login")
    public ResponseEntity<Account> findAccountByUsernameAndPassword(@RequestBody Account account){
        Account tempAccount = accountService.findAccountByUsernameAndPassword(account);

        if(tempAccount != null){
            return ResponseEntity.ok(tempAccount);
        }else{
            return ResponseEntity.status(401).body(tempAccount);
        }
    }

    @PostMapping("/messages")
    public ResponseEntity<Message> saveMessage(@RequestBody Message message){
        Account accountExists = accountService.findAccountById(message.getPostedBy());
        
        if(accountExists == null || message.getMessageText().isBlank() || message.getMessageText().length() > 255){
            return ResponseEntity.status(400).body(null);
        }else{
            return ResponseEntity.ok(messageService.saveMessage(message));
        }

    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages(){
        return ResponseEntity.ok(messageService.getAllMessages());
    }

    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> getMessageById(@PathVariable Integer messageId){
        return ResponseEntity.ok(messageService.getMessageById(messageId));
    }

    @DeleteMapping("messages/{messageId}")
    public ResponseEntity<Integer> deleteMessageById(@PathVariable Integer messageId){
        Optional<Integer> rowsUpdated = messageService.deletMessage(messageId);

        if(rowsUpdated.isEmpty()){
            return ResponseEntity.ok(null);
        }else{
            return ResponseEntity.ok(rowsUpdated.get());
        }
    }

    @PatchMapping("messages/{messageId}")
    public ResponseEntity<Integer> updateMessageById(@PathVariable Integer messageId, @RequestBody Message newMessageText){
        Message messageExists = messageService.getMessageById(messageId);
        
        if(messageExists == null || newMessageText.getMessageText().isBlank() || newMessageText.getMessageText().length() > 255){
            return ResponseEntity.status(400).body(null);
        }else{
            return ResponseEntity.ok(messageService.updateMessage(newMessageText.getMessageText(), messageId));
        }
    }

    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> findAllMessagesByAccountId(@PathVariable Integer accountId){
        return ResponseEntity.ok(messageService.findAllMessagesByAccountId(accountId));
    }
}
