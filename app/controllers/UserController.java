package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import controllers.security.Authenticator;
import controllers.security.IsAdmin;
import daos.UserDao;
import models.User;
import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

public class UserController extends Controller {

    private final static Logger.ALogger LOGGER = Logger.of(UserController.class);

    private UserDao userDao;

    @Inject
    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }

    public Result signUp() {
        return TODO;
    }

    public Result signIn() {

        LOGGER.debug("Sign in");

        final JsonNode jsonNode = request().body().asJson();
        final String username = jsonNode.get("username").asText();
        final String password = jsonNode.get("password").asText();

        if (null == username || null == password) {
            return badRequest();
        }

        // Read User from database using "username"
        final User user = userDao.findbyUsername(username);
        if (null == user) {
            return unauthorized();
        }

        // Compare passwords
        if (!password.equals(user.getPassword())) {
            return unauthorized();
        }

        // General random access token
        final String accessToken = "abc123";

        // Store in database
        user.setAccessToken(accessToken);
        userDao.persist(user);

        // Return to client
        ObjectNode result = Json.newObject();
        result.put("access_token", accessToken);

        return ok(result);
    }

    public Result signOut() {
        return TODO;
    }

    @Authenticator
    public Result getCurrentUser() {

        LOGGER.debug("Get current user");

        final User user = (User) ctx().args.get("user");

        LOGGER.debug("User: {}", user);

        final JsonNode json = Json.toJson(user);
        return ok(json);
    }

    // This method can only be used by admins
    @Authenticator
    @IsAdmin
    public Result deleteUser(String id) {
        return TODO;
    }

    @Authenticator
    public Result updateUser(String id) {
        return TODO;
    }

}
