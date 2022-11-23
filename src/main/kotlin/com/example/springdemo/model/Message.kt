package com.example.springdemo.model

import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Message(@Id val id : String?, val text: String)