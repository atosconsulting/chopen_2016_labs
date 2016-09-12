package ch.open.chef.orders

import io.restassured.RestAssured
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

@RunWith(SpringJUnit4ClassRunner::class)
@SpringBootTest(classes = arrayOf(Application::class), webEnvironment = RANDOM_PORT)
class OrderResourceTest {


    @Value("\${local.server.port}")
    internal var port: Int = 0

    @Before
    fun define_connection() {
        RestAssured.port = port
    }

    @Test
    fun should_return_tables() {
        given().log().all()
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
            .body("[{\"menuId\": \"1232\", \"count\": 1}]")
        .`when`()
            .post("/order")
        .then().log().all()
            .assertThat().statusCode(202)

    }

}