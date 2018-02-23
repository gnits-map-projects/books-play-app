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

        LOGGER.debug("Sign up");

        final JsonNode jsonNode = request().body().asJson();
        final String username = jsonNode.get("username").asText();
        final String password = jsonNode.get("password").asText();
        final String email = jsonNode.get("email").asText();

        if (null == username || null == password || null == email) {
            return badRequest();
        }

        // Username must not be taken
        final User existingUser = userDao.findByUsername(username);
        if (null != existingUser) {
            return status(409, "Username already taken");
        }

        // Generate salt
        final String salt = generateSalt();

        // Hash password
        final String hashedPassword = hashPassword(password, salt, 1000);

        // Create User
        final User user = new User(username, hashedPassword, User.Role.User, salt, 1000);
        userDao.persist(user);

        return created();
    }

    private static String generateSalt() {

        // TODO Generate Salt

        return "YOU_CAN_DO_BETTER";
    }

    private static String hashPassword(String password, String salt, int iteratation) {

        while (iteratation > 0) {

            // TODO Hash

            iteratation--;
        }


        return "FIX_ME";
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
        final User user = userDao.findByUsername(username);
        if (null == user) {
            return unauthorized();
        }

        // Hash password
        final String hashedPassword = hashPassword(password, user.getSalt(), user.getIterations());

        // Compare passwords
        if (!hashedPassword.equals(user.getPasswordHash())) {
            return unauthorized();
        }

        // General random access token
        final String accessToken = generateAccessToken();

        // Store in database
        user.setAccessToken(accessToken);
        userDao.persist(user);

        // Return to client
        ObjectNode result = Json.newObject();
        result.put("access_token", accessToken);

        return ok(result);
    }

    private static String generateAccessToken() {

        // TODO

        return "ACCESS_TOKEN";
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
