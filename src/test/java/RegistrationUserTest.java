import client.UserClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.User;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class RegistrationUserTest {
    public UserClient userClient;
    public User user;
    Response response;

    @Before
    public void setup() {
        userClient = new UserClient();
    }

    @Test
    @DisplayName("Create unique user")
    public void testUserIsCreated() {

        user = User.getRandomUser();
        response = userClient.create(user);
        response.then().assertThat().body("success", equalTo(true)).and().statusCode(HttpStatus.SC_OK);
    }

    @Test
    @DisplayName("Creating a user that already exists")
    public void testUserIsAlreadyRegister() {

        user = User.getRandomUser();
        userClient.create(user);
        response = userClient.create(user);
        response.then().assertThat().body("success", equalTo(false)).and().statusCode(HttpStatus.SC_FORBIDDEN);
    }

    @Test
    @DisplayName("Create user without filling field email")
    public void testUserIsCreatedWithoutEmail() {

        user = User.getRandomUser();
        user.setEmail(null);
        userClient.create(user);
        response = userClient.create(user);
        response.then().assertThat().body("success", equalTo(false)).and().statusCode(HttpStatus.SC_FORBIDDEN);
    }

    @Test
    @DisplayName("Creating a user without the required password field")
    public void testUserIsCreatedWithoutPassword() {

        user = User.getRandomUser();
        user.setPassword(null);
        userClient.create(user);
        response = userClient.create(user);
        response.then().assertThat().body("success", equalTo(false)).and().statusCode(HttpStatus.SC_FORBIDDEN);
    }

    @After
    public void deleteUser() {
        userClient.deleteUser();
    }
}