package com.example.springdemo.controller

import com.example.springdemo.dao.MessageDao
import com.example.springdemo.dao.MessageService
import com.example.springdemo.model.Message
import org.apache.coyote.Response
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * MessageController Class
 * @property [service] a MessageService
 * */
@RestController
class MessageController{
    @Autowired
    private lateinit var messageDao: MessageDao

    @GetMapping
    fun index(): List<Message> = listOf(
        Message("1", "Hello!"),
        Message("2", "Bonjour!"),
        Message("3", "Privet!"),
    )

    @GetMapping("/{id}")
    fun index(@PathVariable id: String): ResponseEntity<Any> {
        var message : Message?
        message =messageDao.findById(id = id).orElse(null)
        if (message==null)
            return ResponseEntity(
                hashMapOf<String,String>(Pair("message","not found")),
                HttpStatus.BAD_GATEWAY)
        return ResponseEntity.ok(message)
    }

    @PostMapping("/message")
    fun addMessage(@RequestBody addedMessage : Message) : ResponseEntity<Any> {
       if(addedMessage.id?.let { messageDao.findById(it) } == ResponseEntity.ok()){
           return ResponseEntity(
               hashMapOf<String,String>(Pair("message","not created ! A message with this id already exists")),
               HttpStatus.BAD_GATEWAY)
       }
        messageDao.save(addedMessage)
        return ResponseEntity.ok(addedMessage)
    }

 @DeleteMapping("/delete/{id}")
    fun deleteMessage(@PathVariable id: String) : ResponseEntity<Any> {

        val message : Message?
        message = messageDao.findById(id = id).orElse(null)
        if(message == null){
            return ResponseEntity(
                hashMapOf<String,String>(Pair("message","not found /!/ Can't delete !")),
                HttpStatus.NOT_FOUND)
        }
        messageDao.deleteById(id)
     return ResponseEntity.ok(message)
    }

}