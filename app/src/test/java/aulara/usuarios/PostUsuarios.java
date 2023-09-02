package aulara.usuarios;

import com.github.javafaker.Faker;
import org.apache.http.HttpStatus;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.is;

public class PostUsuarios {

    @BeforeClass
    public static void setUp() {
        baseURI = "http://localhost";
        port = 3000;
        basePath = "/usuarios";
    }

    @Test
    public void postUserSucess() {
        Faker faker = new Faker();
        given()
                .body("{\n" +
                        "  \"nome\": \"" + faker.dragonBall().character() + "\",\n" +
                        "  \"email\": \"" + faker.internet().emailAddress() + "\",\n" +
                        "  \"password\": \"" + faker.internet().password() + "\",\n" +
                        "  \"administrador\": \"true\" \n" +
                        "}")
                .contentType("application/json")
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .body("message", is("Cadastro realizado com sucesso"));
    }
}
