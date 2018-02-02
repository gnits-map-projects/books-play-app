package models;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class User {

    public enum Role {
        Admin,
        User
    }

    Integer id;

    String username;

    String passwordHash;

    String salt;

    Integer iterations;

    Role role;

    String accessToken;

    Long expirationTimestamp;

    String refreshToken;

    public User(String username, String passwordHash, Role role, String salt, Integer iterations) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.role = role;
        this.salt = salt;
        this.iterations = iterations;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonIgnore
    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @JsonIgnore
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @JsonIgnore
    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    @JsonIgnore
    public Integer getIterations() {
        return iterations;
    }

    public void setIterations(Integer iterations) {
        this.iterations = iterations;
    }
}
