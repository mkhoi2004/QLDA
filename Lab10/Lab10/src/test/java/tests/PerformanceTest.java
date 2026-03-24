package tests;

import io.restassured.RestAssured;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.lessThan;

public class PerformanceTest {

    @Test
    public void testResponseTime() {
        System.out.println("=== Starting Performance Test ===");

        RestAssured.given()
                .baseUri("https://jsonplaceholder.typicode.com")
                .when()
                .get("/users") // API ổn định và nhanh
                .then()
                .statusCode(200)
                .time(lessThan(3000L)); // 3 giây là đủ an toàn

        System.out.println("=== Performance Test PASSED ===");
    }
}