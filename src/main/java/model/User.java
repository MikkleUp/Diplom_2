package model;

import org.apache.commons.lang3.RandomStringUtils;

public class User {
    private String name;
    private String password;
    private String email;

    private static final int MAX_EMAIL_LENGTH = 10;
    private static final int MAX_PASSWORD_LENGTH = 15;
    private static final int MAX_NAME_LENGTH = 10;

    public User() {
    }

    public User(String email, String password, String name) {
    }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public static User getRandomUser() {
        User user = new User();
        user.setEmail(getRandomEmail());
        user.setName(getRandomName());
        user.setPassword(getRandomPassword());
        return user;
    }

    public static String getRandomPassword() {
        return getRandomString(MAX_PASSWORD_LENGTH);
    }

    public static String getRandomName() {
        return getRandomString(MAX_NAME_LENGTH);
    }

    public static String getRandomEmail() {
        return getRandomString(MAX_EMAIL_LENGTH / 2) + "@" + getRandomString(MAX_EMAIL_LENGTH / 2) + "." + getRandomString(2);
    }

    private static String getRandomString(int count) {
        return RandomStringUtils.randomAlphabetic(count);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}