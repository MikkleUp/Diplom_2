package client;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.Order;

import static io.restassured.RestAssured.given;

public class OrderClient extends RestClient {
    private static final String USER_PATH = "/api/orders/";

    @Step("Create order without login")
    public Response createOrderWithoutLogin(Order oder) {
        return given()
                .spec(getBaseSpec())
                .when()
                .body(oder)
                .post(USER_PATH);
    }

    @Step("Create order with login")
    public Response createOrderWithLogin(String accessToken, Order oder) {
        return given()
                .header("Authorization", accessToken)
                .spec(getBaseSpec())
                .when()
                .body(oder)
                .post(USER_PATH);
    }

    @Step("Get user's order with login")
    public Response getOrderUserWithLogin(String accessToken) {
        return given()
                .header("Authorization", accessToken)
                .spec(getBaseSpec())
                .get(USER_PATH);
    }

    @Step("Get user's order without login")
    public Response getOrderUserWithoutLogin() {
        return given()
                .spec(getBaseSpec())
                .get(USER_PATH);
    }
}