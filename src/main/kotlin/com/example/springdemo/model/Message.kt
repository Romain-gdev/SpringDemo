package com.example.springdemo.model

import io.swagger.v3.oas.annotations.media.Schema
import javax.persistence.Entity
import javax.persistence.Id

/**
 * @property id String? Message id
 * @property text String Message text
 */
@Schema(name="Message", description = "A simple Message with an Id and a description (text)")
@Entity
data class Message(@Id var id : String?, val text: String)