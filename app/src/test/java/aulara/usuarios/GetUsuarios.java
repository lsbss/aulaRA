package aulara.usuarios;

import org.apache.http.HttpStatus;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.*;

public class GetUsuarios {

    @BeforeClass
    public static void setUp() {
        baseURI = "http://localhost";
        port = 3000;
        basePath = "/usuarios";
    }

    @Test
    public void getUserSucess() {
        given()
                .when()
                .get()
                .then()
                .statusCode(HttpStatus.SC_OK);
    }
}
