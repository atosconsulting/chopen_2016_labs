package ch.open.chef.orders

import io.restassured.RestAssured
import io.restassured.RestAssured.`when`
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.hasItems
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

@RunWith(SpringJUnit4ClassRunner::class)
@SpringBootTest(classes = arrayOf(Application::class), webEnvironment = RANDOM_PORT)
class TableResourceTest {


    @Value("\${local.server.port}")
    internal var port: Int = 0

    @Before
    fun define_connection() {
        RestAssured.port = port
    }

    @Test
    fun should_return_tables() {
        `when`()
            .get("/tables")
        .then()
            .assertThat().body("size()", `is`(3))
            .and().body("flatten()", hasItems(1, 2, 3))
    }

}