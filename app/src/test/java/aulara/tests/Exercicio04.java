package aulara.tests;

import aulara.Carrinho;
import aulara.Login;
import aulara.Produto;
import aulara.Usuario;
import com.github.javafaker.Faker;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class Exercicio04 {

    private Usuario usuario;
    private Produto produto;
    private Carrinho carrinho;
    private Login login;
    private Faker faker;
    private String userID;
    private String productId;
    private String userToken;

    @Before
    public void setUp() {
        faker = new Faker();
        String userName = faker.dragonBall().character();
        String email = faker.internet().emailAddress();
        String password = faker.internet().password();
        String productName = faker.pokemon().name();
        usuario = new Usuario(userName, email, password, "true");
        login = new Login(email, password);
        produto = new Produto(productName, 1000, "Pokemon do Ash", 10);
        carrinho = new Carrinho();
    }

    @Test
    public void validateStock() {
        userID = usuario.insertUser(usuario);
        userToken = login.loginUser(login);
        productId = produto.insertProduct(produto, userToken);
        carrinho.insertBuyCar(productId, 4, userToken);
        usuario.deleteUserByID(userID, false);
        produto.getProductByID(productId, 6);
        carrinho.cancelPurchase(userToken);
        produto.getProductByID(productId, 10);
        produto.deleteProductByID(productId, userToken);
        usuario.deleteUserByID(userID, true);
    }
}
