package ch.open.chef.orders

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod.POST
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate

@RestController
class OrderResource {

    @Autowired
    lateinit var orderService: OrderService

    @RequestMapping("/order", method = arrayOf(POST), consumes = arrayOf(MediaType.APPLICATION_JSON_VALUE))
    fun order(@RequestBody orders: List<Order>): ResponseEntity<Void>? {
        return orderService.orderInKitchen(orders)
    }
}

@Component
open class OrderService {

    @HystrixCommand(fallbackMethod = "printOrder")
    open fun orderInKitchen(@RequestBody orders: List<Order>): ResponseEntity<Void> {
        val restTemplate = RestTemplate()

        val headers = HttpHeaders()
        headers.contentType = APPLICATION_JSON
        headers.accept = listOf(APPLICATION_JSON)

        restTemplate.postForLocation("http://localhost:8080/v1/menus", HttpEntity<List<Order>>(orders, headers))
        return ResponseEntity.accepted().build()
    }

    open fun printOrder(orders: List<Order>): ResponseEntity<Void> {
        return ResponseEntity.accepted().build()
    }

}

@JsonInclude(NON_EMPTY)
class Order(val menuId: Long, val count: Int) {}