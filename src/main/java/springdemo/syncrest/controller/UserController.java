package springdemo.syncrest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springdemo.syncrest.entites.User;
import springdemo.syncrest.service.UserService;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAllUsers();
    }

    @PostMapping("/{username}/changeRole")
    public CompletableFuture<User> changeUserRole(
            @PathVariable String username,
            @RequestParam String role) {

        return userService.changeRole(username, role);
    }}

