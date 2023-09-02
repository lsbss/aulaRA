package aulara;

import org.apache.http.HttpStatus;
import org.jetbrains.annotations.NotNull;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

public class Login {

    private String email;
    private String password;

    public Login(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String loginUser(@NotNull Login login) {
        return given()
                .body("{\n" +
                        "  \"email\": \"" + login.email + "\",\n" +
                        "  \"password\": \"" + login.password + "\"\n" +
                        "}")
                .contentType("application/json")
                .when()
                .post("http://localhost:3000/login")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("message", is("Login realizado com sucesso"))
                .extract().path("authorization");
    }
}
