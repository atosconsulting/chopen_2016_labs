package ch.open.chef.orders

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
open class Application {

}

fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
}

@RestController
open class TableResource {

    @RequestMapping("/tables")
    fun allTables() = arrayOf(1, 2, 3)

}

