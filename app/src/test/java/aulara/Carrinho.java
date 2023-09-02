package aulara;

import org.apache.http.HttpStatus;
import org.jetbrains.annotations.NotNull;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.is;

public class Carrinho {

    private String idProduto;
    private Integer quantidade;

    public Carrinho() {

    }

    public Carrinho(String idProduto, Integer quantidade) {
        this.idProduto = idProduto;
        this.quantidade = quantidade;
    }

    public void insertBuyCar(String idProduto, Integer quantidade, String userToken) {
        given()
                .header("authorization", userToken)
                .body("{\n" +
                        "  \"produtos\": [\n" +
                        "    {\n" +
                        "      \"idProduto\": \"" + idProduto + "\",\n" +
                        "      \"quantidade\":" + quantidade + "\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}")
                .contentType("application/json")
                .when()
                .post("http://localhost:3000/carrinhos")
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .body("message", is("Cadastro realizado com sucesso"));
    }

    public void cancelPurchase(String userToken) {
        given()
                .header("authorization", userToken)
                .when()
                .delete("http://localhost:3000/carrinhos/cancelar-compra")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("message", is("Registro exclu�do com sucesso. Estoque dos produtos reabastecido"));
    }

}
