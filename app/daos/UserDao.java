package daos;

import daos.CrudDao;
import models.User;

public class UserDao implements CrudDao<Integer, User> {

    private User user = new User("Mattias", "password", User.Role.Admin);

    @Override
    public User persist(User entity) {

        user = entity;

        return null;
    }

    @Override
    public User update(User entity) {
        return null;
    }

    @Override
    public User deleteById(Integer id) {
        return null;
    }

    @Override
    public User findById(Integer id) {
        return null;
    }

    public User findbyUsername(String username) {

        final User user = this.user;

        return user;
    }

    public User findByAccessToken(String accessToken) {

        final User user = this.user;

        return user;
    }


    private User fakeUser() {
        return new User("Mattias", "password", User.Role.User);
    }

}
