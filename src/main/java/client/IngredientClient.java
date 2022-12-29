package client;

import io.qameta.allure.Step;
import model.Ingredient;

import static io.restassured.RestAssured.given;

public class IngredientClient extends RestClient {
    private static final String USER_PATH = "api/ingredients/";

    @Step("Get data about inrgedients")
    public Ingredient getIngredient() {
        return given()
                .spec(getBaseSpec())
                .get(USER_PATH)
                .as(Ingredient.class);
    }
}