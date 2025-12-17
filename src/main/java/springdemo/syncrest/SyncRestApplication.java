package springdemo.syncrest;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springdemo.syncrest.entites.User;
import springdemo.syncrest.repositories.UserRepository;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

@SpringBootApplication
public class SyncRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(SyncRestApplication.class, args);
    }

    @Bean
    CommandLineRunner start(UserRepository userRepository) {
        return args -> {
            String[] usernames = {"alice", "bob", "charlie", "david", "emma",
                    "frank", "grace", "henry", "isabel", "jack"};

            // Création des utilisateurs avec rôle aléatoire
            Stream.of(usernames).forEach(username -> {
                String role = ThreadLocalRandom.current().nextBoolean() ? "Employeur" : "Gestionnaire";
                User user = new User(null, username, role);
                userRepository.save(user);
            });

            System.out.println("10 utilisateurs initiaux créés avec rôles aléatoires !");
        };
    }
}
