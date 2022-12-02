package com.example.springdemo

import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer


@SpringBootApplication
@OpenAPIDefinition(info = Info(title = "Message API", version = "1.0.0"))
class SpringDemoApplication: SpringBootServletInitializer(){

    fun main(args: Array<String>) {
        runApplication<SpringDemoApplication>(*args)

    }
    override fun configure(builder: SpringApplicationBuilder?) :
        SpringApplicationBuilder {
        if (builder != null) {
            return builder.sources(SpringDemoApplication::class.java)
        }
        return super.configure(builder)


    }
}





