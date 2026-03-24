package tests;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class CrudTest {

    private static final String BASE_URI = "https://jsonplaceholder.typicode.com";

    @Test
    public void testCreateUser() {
        String body = """
                {
                    "name": "morpheus",
                    "job": "leader"
                }
                """;

        given()
                .baseUri(BASE_URI)
                .header("Content-Type", "application/json")
                .body(body)
                .when()
                .post("/users")
                .then()
                .statusCode(201)
                .body("name", equalTo("morpheus"))
                .body("job", equalTo("leader"))
                .body("id", notNullValue());
    }

    @Test
    public void testGetUser() {
        given()
                .baseUri(BASE_URI)
                .when()
                .get("/users/2")
                .then()
                .statusCode(200)
                .body("id", equalTo(2))
                .body("name", notNullValue());
    }

    @Test
    public void testUpdateUser() {
        String body = """
                {
                    "name": "neo",
                    "job": "zion resident"
                }
                """;

        given()
                .baseUri(BASE_URI)
                .header("Content-Type", "application/json")
                .body(body)
                .when()
                .put("/users/2")
                .then()
                .statusCode(200)
                .body("name", equalTo("neo"))
                .body("job", equalTo("zion resident"));
    }

    @Test
    public void testDeleteUser() {
        given()
                .baseUri(BASE_URI)
                .when()
                .delete("/users/2")
                .then()
                .statusCode(200); // JSONPlaceholder trả về 200 cho DELETE (không phải 204)
    }
}