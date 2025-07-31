package com.tutorial.idarabi.findfriends.controller

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Controller

@Controller
class MessageController {
    private val logger: Logger
        get() = LoggerFactory.getLogger(MessageController::class.java)

    @MessageMapping("hello-message-mapping")
    @SendTo("/topic/message-topic")
    fun handle(input: String): String {
        logger.info("received an input from websocket $input")
        return "received a message $input";
    }
}