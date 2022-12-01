package com.example.springdemo.model

import javax.persistence.Entity
import javax.persistence.Id

/**
 * @property id String? Message id
 * @property text String Message text
 */
@Entity
data class Message(@Id val id : String?, val text: String)