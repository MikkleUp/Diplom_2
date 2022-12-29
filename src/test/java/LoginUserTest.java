import client.UserClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class LoginUserTest {
    public UserClient userClient;
    public User user;
    Response response;

    @Before
    public void setup() {
        userClient = new UserClient();
        user = User.getRandomUser();
        userClient.create(user);
    }

    @Test
    @DisplayName("Login user with all required fields")
    public void testUserLogin() {
        response = userClient.login(user);
        response.then().assertThat().body("success", equalTo(true)).and().statusCode(200);
    }

    @Test
    @DisplayName("Login user with wrong password")
    public void testUserLoginWithIncorrectPassword() {
        user.setPassword("test");
        response = userClient.login(user);
        response.then().assertThat().body("success", equalTo(false)).and().statusCode(401);
    }

    @Test
    @DisplayName("Login user with wrong Email")
    public void testUserLoginWithIncorrectEmail() {
        user.setEmail("test");
        response = userClient.login(user);
        response.then().assertThat().body("success", equalTo(false)).and().statusCode(401);
    }

    @After
    public void deleteUser() {
        userClient.deleteUser();
    }
}