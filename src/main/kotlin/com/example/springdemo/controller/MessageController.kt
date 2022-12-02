package com.example.springdemo.controller

import com.example.springdemo.dao.MessageDao
import com.example.springdemo.model.Message
import io.swagger.annotations.Example
import io.swagger.annotations.ExampleProperty
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.media.Content
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.data.repository.query.Param
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.lang.Byte.decode

/**
 * MessageController Class
 * @property [service] a MessageService
 * */
@RestController
@RequestMapping("/message")
class MessageController{
    @Autowired
    private lateinit var messageDao: MessageDao


    @Operation(summary = "Home page", description = "Home page / Welcome Page")
    @GetMapping
    fun index(): List<Message> = emptyList()
        /*listOf(
        Message("1", "Hello!"),
        Message("2", "Welcome to"),
        Message("3", "Message Api!"),
    )*/

    @Operation(summary = "Get Message", description = "Get a message by sending id to server")
    @Parameter(name="id", description = "Message id")
    @ApiResponses(ApiResponse(responseCode = "200",
        description = "OK", content = [
        Content(mediaType = "application/json", schema = Schema(implementation = Message::class))
    ]),
        ApiResponse(responseCode = "404",
            description = "Not Found", content = [
                Content(mediaType = "application/json", schema = Schema(type ="object", example = "{\n \"message\" : \"not found !\" \n}"))]
        ))
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

    @Operation(summary="Modify Message", description = "Modify Message content (text atttribute)")
    @ApiResponses(ApiResponse(responseCode = "200",
        description = "OK", content = [
            Content(mediaType = "application/json", schema = Schema(implementation = Message::class))
        ]),
        ApiResponse(responseCode = "404",
            description = "Not Found", content = [
                Content(mediaType = "application/json", schema = Schema(type ="object", example = "{\n \"message\" : \"not found !\" \n}"))]
        ))
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

    @Operation(summary = "create Message", description = "create a new Message")
    @ApiResponses(ApiResponse(responseCode = "201",
        description = "Created", content = [
            Content(mediaType = "application/json", schema = Schema(implementation = Message::class))
        ]),
        ApiResponse(responseCode = "402",
            description = "Already Exists", content = [
                Content(mediaType = "application/json", schema = Schema(type ="object", example = "{\n \"message\" : \"not created ! A message with this id already exists\" \n}"))]
        ))
    @PostMapping("/message")
    fun addMessage(@RequestBody addedMessage : Message) : ResponseEntity<Any> {
       if(addedMessage.id?.let { messageDao.findById(it) } == ResponseEntity.ok()){
           return ResponseEntity(
               hashMapOf<String,String>(Pair("message","not created ! A message with this id already exists")),
               HttpStatus.CONFLICT)
       }
        messageDao.save(addedMessage)
        return ResponseEntity(addedMessage,HttpStatus.CREATED)
    }
    @Operation(summary = "delete Message", description = "delete Message by sending id to server")
    @ApiResponses(ApiResponse(responseCode = "200",
        description = "OK", content = [
            Content(mediaType = "application/json", schema = Schema(implementation = Message::class))
        ]),
        ApiResponse(responseCode = "404",
            description = "Not found", content = [
                Content(mediaType = "application/json", schema = Schema(type ="object", example = "{\n \"message\" : \"not found\" \n}"))]
        ))
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