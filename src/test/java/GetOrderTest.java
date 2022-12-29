import client.IngredientClient;
import client.OrderClient;
import client.UserClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.Ingredient;
import model.Order;
import model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;

public class GetOrderTest {
    public User user;
    public UserClient userClient;
    public OrderClient orderClient;
    public Order order;
    public Ingredient allIngredient;
    public IngredientClient ingredientClient;
    private String accessToken;
    public List<String> ingredients;
    Response response;

    @Before
    public void setup() {
        ingredientClient = new IngredientClient();
        allIngredient = ingredientClient.getIngredient();
        ingredients = new ArrayList<>();
        ingredients.add(allIngredient.data.get(0).get_id());
        ingredients.add(allIngredient.data.get(1).get_id());
        ingredients.add(allIngredient.data.get(2).get_id());

        user = User.getRandomUser();
        userClient = new UserClient();
        userClient.create(user);
        Response responseForToken = userClient.login(user);
        accessToken = responseForToken.then().extract().path("accessToken");
        order = new Order(ingredients);
        orderClient = new OrderClient();
        orderClient.createOrderWithLogin(accessToken, order);
    }

    @Test
    @DisplayName("Get order login's user")
    public void getOderForUserWithAuthorizationTest() {
        response = orderClient.getOrderUserWithLogin(accessToken);
        response.then().assertThat().body("success", equalTo(true)).and().statusCode(200);
    }

    @Test
    @DisplayName("Get order for unauthorized user")
    public void getOderForUserWithoutAuthorizationTest() {

        response = orderClient.getOrderUserWithoutLogin();
        response.then().assertThat().body("success", equalTo(false)).and().statusCode(401);
    }

    @After
    public void deleteUser(){
        userClient.deleteUser();
    }
}