package springdemo.syncrest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springdemo.syncrest.entites.User;
import springdemo.syncrest.repositories.UserRepository;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User addNewUser(String username, String role) {
        User userExist = this.userRepository.getUserByUsername(username);
        if (userExist != null) {
            throw new RuntimeException("Username exist");
        }

        User newUser = new User(null, username, role);
        User userSaved = this.userRepository.save(newUser);

        return userSaved;
    }

    @Override
    public User findUserByUsername(String username) {
        User userExist = this.userRepository.getUserByUsername(username);

        if (userExist == null) {
            throw new RuntimeException("User not found");
        }

        return userExist;
    }

    @Override
    public CompletableFuture<User> changeRole(String username, String role) {
        System.out.println("Thread changeRole START: " + Thread.currentThread().getName());

        return CompletableFuture.supplyAsync(() -> {
            System.out.println("Thread supplyAsync: " + Thread.currentThread().getName());
            try {
                Thread.sleep(2000);
                return findUserByUsername(username);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).thenCompose(user -> {
            System.out.println("Thread thenCompose: " + Thread.currentThread().getName());
            try {
                return changeUserRole(user, role);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }


    private CompletableFuture<User> changeUserRole(User user, String role) throws InterruptedException {
        System.out.println("Thread changeUserRole START: " + Thread.currentThread().getName());
        Thread.sleep(2000);

        user.setRole(role);
        User userSaved = userRepository.save(user);

        return CompletableFuture.completedFuture(userSaved);
    }


    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
}


