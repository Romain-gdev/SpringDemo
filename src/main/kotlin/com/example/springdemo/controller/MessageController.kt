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
class MessageController(val service : MessageService) {
    @Autowired
    private lateinit var messageDao: MessageDao

    @GetMapping
    fun index(): List<Message> = messageDao.findAll()

    @GetMapping("/{id}")
    fun index(@PathVariable id: String): ResponseEntity<Any> {
        val message : Message?
        message = messageDao.findById(id = id).orElse(null)
        if(message == null) return ResponseEntity(hashMapOf<String,String>(Pair("message","not found")),HttpStatus.NOT_FOUND)
        return ResponseEntity.ok(message)
    }

    @PostMapping(value = arrayOf("/createMessage"))
    fun addMessage(addedMessage : Message) : ResponseEntity<Any> {
        val message : Message?
        message = messageDao.save(addedMessage)
        if(message == null) return ResponseEntity(hashMapOf<String,String>(Pair("message","not created")),HttpStatus.NOT_MODIFIED)
        return ResponseEntity(hashMapOf<String,String>(Pair("message","created")),HttpStatus.CREATED)

    }
    

    @PostMapping
    fun post(@RequestBody message: Message) {
        service.save(message)
    }
}