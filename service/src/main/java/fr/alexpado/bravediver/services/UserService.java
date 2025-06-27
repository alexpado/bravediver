package fr.alexpado.bravediver.services;

import fr.alexpado.bravediver.entities.User;
import fr.alexpado.bravediver.web.oauth.results.DiscordIdentity;
import fr.alexpado.bravediver.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {

        this.repository = repository;
    }

    public void ensureUserExists(DiscordIdentity identity) {

        Optional<User> optionalUser = this.findFromIdentity(identity);
        if (optionalUser.isPresent()) return;

        User user = new User();
        user.setId(identity.getId());
        user.setUsername(identity.getUsername());
        this.repository.save(user);
    }

    public Optional<User> findFromIdentity(DiscordIdentity identity) {

        return this.repository.findById(identity.getId());
    }

}
