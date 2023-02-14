package com.example.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class DemoApplication

fun main(args: Array<String>) {
	runApplication<DemoApplication>(*args)
}

data class Greeting(
	val greeting: String = "Hello",
	val who: String = "World"
)

@RestController
class HelloWorldController(){

	@GetMapping("/hello")
	fun helloWorld(): Greeting {
		println("Hello World")
		return Greeting()
	}
}