package aulara;

import org.apache.http.HttpStatus;
import org.jetbrains.annotations.NotNull;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.is;

public class Usuario {

    private String nome;
    private String email;
    private String password;
    private String administrador;

    public Usuario(String nome, String email, String password, String administrador) {
        this.nome = nome;
        this.email = email;
        this.password = password;
        this.administrador = administrador;
    }

    public void getUsers() {
        when()
                .get("http://localhost:3000/usuarios")
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    public void getUserByID(@NotNull String userID) {
        given()
                .pathParam("_id", userID)
                .when()
                .get("http://localhost:3000/usuarios/{_id}")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("_id", is(userID));
    }

    public String insertUser(@NotNull Usuario usuario) {
        return given()
                .body("{\n" +
                        "  \"nome\": \"" + usuario.nome + "\",\n" +
                        "  \"email\": \"" + usuario.email + "\",\n" +
                        "  \"password\": \"" + usuario.password + "\",\n" +
                        "  \"administrador\": \"" + usuario.administrador + "\" \n" +
                        "}")
                .contentType("application/json")
                .when()
                .post("http://localhost:3000/usuarios")
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .body("message", is("Cadastro realizado com sucesso"))
                .extract().path("_id");
    }

    public void deleteUserByID(@NotNull String userID, Boolean semCarrinho) {
        String message = "Registro excluído com sucesso";
        int statusCode = HttpStatus.SC_OK;
        if(!semCarrinho) {
            statusCode = HttpStatus.SC_BAD_REQUEST;
            message = "Não é permitido excluir usuário com carrinho cadastrado";
        }

        given()
                .pathParam("_id", userID)
                .when()
                .delete("http://localhost:3000/usuarios/{_id}")
                .then()
                .statusCode(statusCode)
                .body("message", is(message));
    }

    public void editUser(@NotNull String userID, @NotNull Usuario usuario, @NotNull Boolean exists) {

        String message;
        int statusCode = HttpStatus.SC_OK;

        if (exists) {
            message = "Registro alterado com sucesso";
        } else {
            statusCode = HttpStatus.SC_CREATED;
            message = "Cadastro realizado com sucesso";
        }

        given()
                .pathParam("_id", userID)
                .body("{\n" +
                        "  \"nome\": \"" + usuario.nome + "\",\n" +
                        "  \"email\": \"" + usuario.email + "\",\n" +
                        "  \"password\": \"" + usuario.password + "\",\n" +
                        "  \"administrador\": \"" + usuario.administrador + "\" \n" +
                        "}")
                .contentType("application/json")
                .when()
                .put("http://localhost:3000/usuarios/{_id}")
                .then()
                .statusCode(statusCode)
                .body("message", is(message));
    }
}
