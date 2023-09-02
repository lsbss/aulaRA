package aulara;

import org.apache.http.HttpStatus;
import org.jetbrains.annotations.NotNull;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.is;

public class Produto {

    private String nome;
    private Integer preco;
    private String descricao;
    private Integer quantidade;

    public Produto(String nome, Integer preco, String descricao, Integer quantidade) {
        this.nome = nome;
        this.preco = preco;
        this.descricao = descricao;
        this.quantidade = quantidade;
    }

    public void getProduct() {
        when()
                .get("http://localhost:3000/produtos")
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    public String insertProduct(@NotNull Produto produto, String userToken) {
        return given()
                .header("authorization", userToken)
                .body("{\n" +
                        "  \"nome\": \"" + produto.nome + "\",\n" +
                        "  \"preco\":\"" + produto.preco + "\", \n" +
                        "  \"descricao\": \"" + produto.descricao + "\",\n" +
                        "  \"quantidade\": \"" + produto.quantidade + "\"\n" +
                        "}")
                .contentType("application/json")
                .when()
                .post("http://localhost:3000/produtos")
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .body("message", is("Cadastro realizado com sucesso"))
                .extract().path("_id");
    }

    public void getProductByID(String productID, Integer quantidade) {
        given()
                .pathParam("_id", productID)
                .when()
                .get("http://localhost:3000/produtos/{_id}")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("quantidade", is(quantidade))
                .body("_id", is(productID));
    }

    public void deleteProductByID(String productID, String userToken) {
        given()
                .pathParam("_id", productID)
                .header("authorization", userToken)
                .when()
                .delete("http://localhost:3000/produtos/{_id}")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("message", is("Registro excluído com sucesso"));
    }

}
