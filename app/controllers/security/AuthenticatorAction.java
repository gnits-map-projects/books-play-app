package controllers.security;

import com.google.inject.Inject;
import daos.UserDao;
import models.User;
import play.Logger;
import play.db.jpa.JPAApi;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;

import javax.persistence.EntityManager;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class AuthenticatorAction extends Action.Simple {

    private final static Logger.ALogger LOGGER = Logger.of(AuthenticatorAction.class);

    private UserDao userDao;
    private JPAApi jpaApi;

    @Inject
    public AuthenticatorAction(UserDao userDao, JPAApi jpaApi) {
        this.userDao = userDao;
        this.jpaApi = jpaApi;
    }

    @Override
    public CompletionStage<Result> call(Http.Context ctx) {

        LOGGER.debug("AuthenticatorAction2");

        // Get authorization header
        final Optional<String> header = ctx.request().header("Authorization");
        LOGGER.debug("Header: {}", header);
        if (!header.isPresent()) {
            return CompletableFuture.completedFuture(unauthorized());
        }

        // Extract access_token
        if (!header.get().startsWith("Bearer ")) {
            return CompletableFuture.completedFuture(unauthorized());
        }

        final String accessToken = header.get().substring(7);
        if (accessToken.isEmpty()) {
            return CompletableFuture.completedFuture(unauthorized());
        }
        LOGGER.debug("Access token: {}", accessToken);

        // All database operations must happen within a transaction
        // Create an explicit transaction
        // The code inside withTransaction block will be run within a transaction
        // You can also consider moving this code inside UserDao class
        final User user = jpaApi.withTransaction(entityManager -> {
            // Get user by access token from database
            return userDao.findByAccessToken(accessToken);
        });

        if (null == user) {
            return CompletableFuture.completedFuture(unauthorized());
        }

        // Check expiration
        // TODO

        LOGGER.debug("User: {}", user);

        ctx.args.put("user", user);

        return delegate.call(ctx);
    }

}
