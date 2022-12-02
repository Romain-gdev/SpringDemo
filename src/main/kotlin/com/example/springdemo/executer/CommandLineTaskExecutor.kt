package com.example.springdemo.executer

import com.example.springdemo.dao.MessageDao
import com.example.springdemo.model.Message
import com.example.springdemo.service.TaskService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import com.opencsv.CSVReader
import java.io.FileReader
import java.io.InputStream

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

        taskService.execute("Begin Parsing CSV data...")
        fileReader.use {
            val reader = CSVReader(fileReader)
            reader.use {r ->
                r.readNext()
                var line = r.readNext()
                taskService.execute("reading")
                while(line!=null){
                    var message : Message?
                    message = Message(id = line.get(0), text = line.get(1))
                    messageDao.save(message)
                    line = r.readNext()
                }
            }
        }
        taskService.execute("data succesfully added !")

    }

    override fun run(vararg args: String?) {
        taskService.execute("command line runner task")
        readCsvAndSave()
    }

}