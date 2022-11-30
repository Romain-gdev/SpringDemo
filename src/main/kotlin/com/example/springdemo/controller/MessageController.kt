package com.example.springdemo.controller

import com.example.springdemo.dao.MessageDao
import com.example.springdemo.model.Message
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import io.swagger.annotations.Example
import io.swagger.annotations.ExampleProperty
import io.swagger.v3.oas.annotations.Operation
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

    @Operation
    @GetMapping("/{id}")
    @ApiResponses(value = arrayOf(ApiResponse(code =200, message = "o",examples = Example(ExampleProperty("{\n \"id\" :\"1\" \n \"message\" :\"message 1\" \n}"))),
        ApiResponse(code =502, message = "Not found",examples = Example(ExampleProperty("{\"message\" :\"not found\" \n}")))
            ))
    fun index(@PathVariable id: String): ResponseEntity<Any> {
        var message : Message?
        message =messageDao.findById(id = id).orElse(null)
        if (message==null)
            return ResponseEntity(
                hashMapOf<String,String>(Pair("message","not found")),
                HttpStatus.BAD_GATEWAY)
        return ResponseEntity.ok(message)
    }
    @Operation
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
    @Operation
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
@Operation
 @PutMapping("/message/{id}")
    fun modifyMessage(@RequestBody message: Message) : ResponseEntity<Any> {

         if(message == null){
         return ResponseEntity(
             hashMapOf<String,String>(Pair("message","not found /!/ Can't Modify !")),
             HttpStatus.NOT_FOUND)
        }
        messageDao.save(message)
        return ResponseEntity.ok(message)
    }

}