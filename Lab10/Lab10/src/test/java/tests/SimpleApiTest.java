
package tests;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class SimpleApiTest {

    @Test
    public void testGetUsers() {
        given()
        .when()
            .get("https://reqres.in/api/users?page=1")
        .then()
            .statusCode(200)
            .body("data.size()", greaterThan(0));
    }
}
