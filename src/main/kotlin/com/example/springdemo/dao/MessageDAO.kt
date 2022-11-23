package com.example.springdemo.dao

import com.example.springdemo.model.Message
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface MessageDao: JpaRepository<Message, String> {
    override fun findById(id: String): Optional<Message>
    override fun <S : Message> save(entity: S): S
}
