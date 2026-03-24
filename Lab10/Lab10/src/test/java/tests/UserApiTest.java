package tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import com.github.tomakehurst.wiremock.WireMockServer;
import static com.github.tomakehurst.wiremock.client.WireMock.*;   // Keep for stubbing
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;   // Keep for assertions

// NEW: Explicitly import Hamcrest equalTo to override WireMock's equalTo
import static org.hamcrest.Matchers.equalTo;

public class UserApiTest {

    private WireMockServer wireMockServer;
    private static final String BASE_URL = "http://localhost:8080";

    @BeforeClass
    public void setup() {
        wireMockServer = new WireMockServer(8080);
        wireMockServer.start();
        configureWireMock();
    }

    @AfterClass
    public void teardown() {
        wireMockServer.stop();
    }

    private void configureWireMock() {
        // Mock GET /users/1
        stubFor(get(urlEqualTo("/api/users/1"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(
                                "{\"data\":{\"id\":1,\"email\":\"george@reqres.in\",\"first_name\":\"George\",\"last_name\":\"Bluth\"}}")));

        // Mock GET /users?page=1
        stubFor(get(urlEqualTo("/api/users?page=1"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(
                                "{\"page\":1,\"per_page\":6,\"total\":12,\"total_pages\":2,\"data\":[{\"id\":1,\"email\":\"george@reqres.in\",\"first_name\":\"George\"},{\"id\":2,\"email\":\"janet@reqres.in\",\"first_name\":\"Janet\"}]}")));

        // Mock POST /users
        stubFor(post(urlEqualTo("/api/users"))
                .willReturn(aResponse()
                        .withStatus(201)
                        .withHeader("Content-Type", "application/json")
                        .withBody(
                                "{\"name\":\"morpheus\",\"job\":\"leader\",\"id\":\"915\",\"createdAt\":\"2023-01-01T00:00:00.000Z\"}")));

        // Mock PUT /users/1
        stubFor(put(urlEqualTo("/api/users/1"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(
                                "{\"name\":\"updated\",\"job\":\"developer\",\"updatedAt\":\"2023-01-01T00:00:00.000Z\"}")));

        // Mock DELETE /users/1
        stubFor(delete(urlEqualTo("/api/users/1"))
                .willReturn(aResponse()
                        .withStatus(204)));

        // Mock GET /users/999999 (not found)
        stubFor(get(urlEqualTo("/api/users/999999"))
                .willReturn(aResponse()
                        .withStatus(404)));
    }

    @Test
    public void testGetSingleUser() {
        given()
                .when()
                .get(BASE_URL + "/api/users/1")
                .then()
                .statusCode(200)
                .body("data.id", equalTo(1))
                .body("data.email", notNullValue())
                .body("data.first_name", notNullValue());
    }

    @Test
    public void testGetAllUsers() {
        given()
                .when()
                .get(BASE_URL + "/api/users?page=1")
                .then()
                .statusCode(200)
                .body("data.size()", greaterThan(0))
                .body("total", greaterThan(0));
    }

    @Test
    public void testCreateUser() {
        given()
                .contentType("application/json")
                .body("{\"name\":\"morpheus\",\"job\":\"leader\"}")
                .when()
                .post(BASE_URL + "/api/users")
                .then()
                .statusCode(201)
                .body("name", equalTo("morpheus"))   // now uses Hamcrest equalTo
                .body("job", equalTo("leader"))
                .body("id", notNullValue());
    }

    @Test
    public void testUpdateUser() {
        given()
                .contentType("application/json")
                .body("{\"name\":\"updated\",\"job\":\"developer\"}")
                .when()
                .put(BASE_URL + "/api/users/1")
                .then()
                .statusCode(200)
                .body("name", equalTo("updated"))    // now uses Hamcrest equalTo
                .body("job", equalTo("developer"));
    }

    @Test
    public void testDeleteUser() {
        given()
                .when()
                .delete(BASE_URL + "/api/users/1")
                .then()
                .statusCode(204);
    }

    @Test
    public void testInvalidUserReturns404() {
        given()
                .when()
                .get(BASE_URL + "/api/users/999999")
                .then()
                .statusCode(404);
    }

    @Test
    public void testUserEmailFormatValidation() {
        given()
                .when()
                .get(BASE_URL + "/api/users/1")
                .then()
                .statusCode(200)
                .body("data.email", matchesPattern("^[A-Za-z0-9+_.-]+@(.+)$"));
    }
}