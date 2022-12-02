package com.example.springdemo.executer

import com.example.springdemo.dao.MessageDao
import com.example.springdemo.service.TaskService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import com.opencsv.CSVReader
import java.io.FileReader

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

    fun readCsvAndSave() {
        val fileName = this::class.java.classLoader.getResource("data.csv")?.file
        val fileReader = fileName?.let { FileReader(it) }

        fileReader.use {
            val reader = CSVReader(fileReader)
            reader.use {r ->
                var line = r.readNext()
                while(line!=null){

                        print("${line[1]}")

                    line = r.readNext()
                }
            }
        }

    }

    override fun run(vararg args: String?) {
        taskService.execute("command line runner task")
        println("TODO parse CSV")
        readCsvAndSave()
    }

}