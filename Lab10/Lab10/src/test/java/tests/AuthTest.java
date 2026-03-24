package tests;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class AuthTest {

    private WireMockServer wireMockServer;
    private static final int PORT = 8089;
    private static final String BASE_URL = "http://localhost:" + PORT;

    @BeforeClass
    public void setup() {
        wireMockServer = new WireMockServer(PORT);
        wireMockServer.start();

        // Cấu hình WireMock client dùng đúng port
        WireMock.configureFor("localhost", PORT);

        configureStubs();
    }

    @AfterClass
    public void teardown() {
        if (wireMockServer != null && wireMockServer.isRunning()) {
            wireMockServer.stop();
        }
    }

    private void configureStubs() {
        // Login thành công
        WireMock.stubFor(WireMock.post(WireMock.urlEqualTo("/api/login"))
                .withRequestBody(WireMock.matchingJsonPath("$.email", WireMock.equalTo("eve.holt@reqres.in")))
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"token\": \"QpwL5tke4Pnpja7X4\"}")));

        // Login thất bại
        WireMock.stubFor(WireMock.post(WireMock.urlEqualTo("/api/login"))
                .withRequestBody(WireMock.matchingJsonPath("$.email", WireMock.equalTo("wrong@mail.com")))
                .willReturn(WireMock.aResponse()
                        .withStatus(400)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"error\": \"Missing email or username\"}")));
    }

    @DataProvider(name = "loginData")
    public Object[][] data() {
        return new Object[][] {
                { "eve.holt@reqres.in", "cityslicka", 200 },
                { "wrong@mail.com", "123", 400 }
        };
    }

    @Test(dataProvider = "loginData")
    public void testLogin(String email, String password, int expectedStatus) {

        String body = """
                {
                    "email": "%s",
                    "password": "%s"
                }
                """.formatted(email, password);

        given()
                .baseUri(BASE_URL)
                .header("Content-Type", "application/json")
                .body(body)
                .when()
                .post("/api/login")
                .then()
                .statusCode(is(expectedStatus));
    }
}