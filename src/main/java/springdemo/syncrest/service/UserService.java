package springdemo.syncrest.service;

import springdemo.syncrest.entites.User;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface UserService {
    User addNewUser(String username, String role);
    User findUserByUsername(String username);
    CompletableFuture<User> changeRole(String username, String role);
    List<User> findAllUsers();
}
