package com.example.springdemo.service

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

/**
 * Class to log actions when parsing data.csv
 */
@Service
class TaskService {

    fun execute(task: String){
        logger.info("$task")
    }

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(TaskService::class.java)
    }
}