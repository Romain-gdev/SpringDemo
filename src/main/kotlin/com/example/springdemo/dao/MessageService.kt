package com.example.springdemo.dao

import com.example.springdemo.model.Message
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.servlet.function.EntityResponse
import java.util.*

@Service
class MessageService(val db : MessageRepository) {
    fun findMessages() : List<Message> = db.findAll().toList()
    fun findMessageById(id: String): List<Message> = db.findById(id).toList()
    fun save(message: Message) {
        db.save(message)
    }
    fun delete(id : String) {
        db.deleteById(id)
    }
    fun <T : Any> Optional<out T>.toList(): List<T> =
        if (isPresent) listOf(get()) else emptyList()

}