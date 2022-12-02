package com.example.springdemo.executer

import com.example.springdemo.dao.MessageDao
import com.example.springdemo.service.TaskService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Profile("!test")
@ConditionalOnProperty(
    prefix= "command.line.runner",
    value=arrayOf("enabled"),
    havingValue="true",
    matchIfMissing=true
)
@Component
class CommandLineTaskExecutor(private val taskService: TaskService): CommandLineRunner {
    @Autowired
    private lateinit var messageDao: MessageDao

    override fun run(vararg args: String?) {
        taskService.execute("command line runner task")
        println("TODO parse CSV")
    }

}