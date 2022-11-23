package com.example.springdemo.dao

import com.example.springdemo.model.Message
import org.springframework.data.repository.CrudRepository

interface MessageRepository : CrudRepository<Message, String>