package tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class AuthTest {

    @DataProvider(name = "authData")
    public Object[][] data() {
        return new Object[][] {
                { "eve.holt@reqres.in", "pistol", 200 },     // Thành công
                { "wrong@mail.com",     "123",   400 }       // Thất bại
        };
    }

    @Test(dataProvider = "authData")
    public void testAuth(String email, String password, int expectedStatus) {

        String body = """
                {
                    "email": "%s",
                    "password": "%s"
                }
                """.formatted(email, password);

        given()
                .baseUri("https://reqres.in")
                .header("Content-Type", "application/json")
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36") // Giảm khả năng bị block
                .body(body)
                .when()
                .post("/api/register")        // Dùng /register thay vì /login
                .then()
                .statusCode(expectedStatus);
    }
}