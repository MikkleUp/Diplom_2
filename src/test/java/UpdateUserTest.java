import client.UserClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.User;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class UpdateUserTest {
    public UserClient userClient;
    public User user;
    private String accessToken;

    @Before
    public void setup() {
        userClient = new UserClient();
        user = User.getRandomUser();
        Response response = userClient.create(user);
        accessToken = response.then().extract().path("accessToken");
    }

    @Test
    @DisplayName("Update Email for authorization user")
    public void UserAuthorizationUpdateEmailTest() {
        userClient.getDataUser(accessToken);
        user.setEmail(User.getRandomEmail());
        Response response = userClient.setDataUserWithToken(accessToken, new User(user.getEmail(), user.getName()));
        response.then().assertThat().body("success", equalTo(true)).and().statusCode(HttpStatus.SC_OK);
    }

    @Test
    @DisplayName("Update name of user for authorization user")
    public void UserAuthorizationUpdateNameTest() {
        userClient.getDataUser(accessToken);
        user.setName(User.getRandomName());
        Response response = userClient.setDataUserWithToken(accessToken, new User(user.getEmail(), user.getName()));
        response.then().assertThat().body("success", equalTo(true)).and().statusCode(HttpStatus.SC_OK);
    }

    @Test
    @DisplayName("Update login of user for unauthorization user")
    public void UserNoAuthorizationUpdateEmailTest() {
        userClient.getDataUser(accessToken);
        user.setEmail(User.getRandomEmail());
        Response response = userClient.setDataUserWithoutToken(new User(user.getEmail(), user.getName()));
        response.then().assertThat().body("success", equalTo(false)).and().statusCode(HttpStatus.SC_UNAUTHORIZED);
    }

    @Test
    @DisplayName("Update name of user for unauthorization user ")
    public void UserNoAuthorizationUpdateNameTest() {
        userClient.getDataUser(accessToken);
        user.setName(User.getRandomName());
        Response response = userClient.setDataUserWithoutToken(new User(user.getEmail(), user.getName()));
        response.then().assertThat().body("success", equalTo(false)).and().statusCode(HttpStatus.SC_UNAUTHORIZED);
    }

    @After
    public void deleteUser() {
        userClient.deleteUser();
    }
}