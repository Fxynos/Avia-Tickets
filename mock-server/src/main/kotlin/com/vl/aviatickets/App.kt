package com.vl.aviatickets

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder

@SpringBootApplication
open class App {
    companion object{
        @JvmStatic
        fun main(vararg args: String) {
            SpringApplicationBuilder(App::class.java).run(*args)
        }
    }
}