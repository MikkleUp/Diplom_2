import client.IngredientClient;
import client.OrderClient;
import client.UserClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.Ingredient;
import model.Order;
import model.User;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;


public class CreateOrderTest {
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
        ingredients.add(allIngredient.data.get(0).getId());
        ingredients.add(allIngredient.data.get(1).getId());
        ingredients.add(allIngredient.data.get(2).getId());
        user = User.getRandomUser();
        userClient = new UserClient();
        userClient.create(user);
        Response responseForToken = userClient.login(user);
        responseForToken.then().assertThat().body("success", equalTo(true)).and().statusCode(HttpStatus.SC_OK);
        accessToken = responseForToken.then().extract().path("accessToken");

    }

    @Test
    @DisplayName("Creating an order with authorization")
    public void createOderWithAuthorizationTest() {
        order = new Order(ingredients);
        orderClient = new OrderClient();
        response = orderClient.createOrderWithLogin(accessToken, order);
        response.then().assertThat().body("success", equalTo(true)).and().statusCode(HttpStatus.SC_OK);

    }


    @Test
    @DisplayName("Creating an order without authorization")
    public void createOderWithoutAuthorizationTest() {
        orderClient = new OrderClient();
        order = new Order(ingredients);
        response = orderClient.createOrderWithoutLogin(order);
        response.then().assertThat().statusCode(HttpStatus.SC_OK).and().body("success", equalTo(true));
    }

    @Test
    @DisplayName("Create order with ingredients")
    public void createOderWithIngredient() {

        order = new Order(ingredients);
        orderClient = new OrderClient();
        response = orderClient.createOrderWithLogin(accessToken, order);
        response.then().assertThat().statusCode(HttpStatus.SC_OK);
    }

    @Test
    @DisplayName("Create order without ingredients")
    public void createOrderWithNullIngredient() {

        ingredients.clear();
        order = new Order(ingredients);
        orderClient = new OrderClient();
        response = orderClient.createOrderWithLogin(accessToken, order);
        response.then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("Create order with wrong hash ingredients")
    public void createOderWithIncorrectIngredient() {

        ingredients.add("IncorrectIngredient");
        order = new Order(ingredients);
        orderClient = new OrderClient();
        response = orderClient.createOrderWithLogin(accessToken, order);
        response.then().assertThat().statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }

    @After
    public void deleteUser(){
        userClient.deleteUser();
    }

}