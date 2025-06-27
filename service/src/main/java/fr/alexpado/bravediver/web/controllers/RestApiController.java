package fr.alexpado.bravediver.web.controllers;

import fr.alexpado.bravediver.entities.Stratagem;
import fr.alexpado.bravediver.entities.User;
import fr.alexpado.bravediver.repositories.StratagemRepository;
import fr.alexpado.bravediver.repositories.UserRepository;
import fr.alexpado.bravediver.web.WebResponses;
import fr.alexpado.bravediver.web.oauth.AuthenticationManager;
import jakarta.servlet.http.HttpServletRequest;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1")
public class RestApiController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestApiController.class);

    private final AuthenticationManager authManager;
    private final StratagemRepository   stratagemRepository;
    private final UserRepository        userRepository;
    private final WebResponses          responses;

    public RestApiController(AuthenticationManager authManager, StratagemRepository stratagemRepository, UserRepository userRepository, WebResponses responses) {

        this.authManager         = authManager;
        this.stratagemRepository = stratagemRepository;
        this.userRepository      = userRepository;
        this.responses           = responses;
    }

    private Optional<User> getAuthenticatedUser(HttpServletRequest request) {

        return this.authManager.getUserFromSession(request.getHeader("x-session"));
    }

    @GetMapping("/auth/{code}")
    public ResponseEntity<String> authenticate(@PathVariable String code) {

        try {
            UUID session = this.authManager.authenticate(code);
            return ResponseEntity.ok(this.responses.authResult(session));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(this.responses.authResult(e));
        }
    }

    @GetMapping(value = "/data", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> applicationData() {

        List<Stratagem> stratagems = this.stratagemRepository.findAll();
        return ResponseEntity.ok(this.responses.applicationData(stratagems));
    }

    @GetMapping(value = "/me", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> userData(HttpServletRequest request) {

        return this.getAuthenticatedUser(request)
                   .map(user -> ResponseEntity.ok(this.responses.userProfile(user)))
                   .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(this.responses.userProfile(null)));
    }

    @PostMapping(value = "/me", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> userData(HttpServletRequest request, @RequestBody String body) {

        Optional<User> optUser = this.getAuthenticatedUser(request);
        if (optUser.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(this.responses.guest("Unavailable while not logged in."));
        }
        User       user = optUser.get();
        JSONObject query;

        try {
            query = new JSONObject(body);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(this.responses.error("Invalid JSON received."));

        }

        JSONArray stratagemArray = query.has("stratagems") ? query.getJSONArray("stratagems") : new JSONArray();
        JSONArray weaponArray    = query.has("weapons") ? query.getJSONArray("weapons") : new JSONArray();

        Collection<Integer> stratagemIds = new ArrayList<>();
        Collection<Integer> weaponIds    = new ArrayList<>();

        for (int i = 0; i < stratagemArray.length(); i++) {
            stratagemIds.add(stratagemArray.getInt(i));
        }

        for (int i = 0; i < weaponArray.length(); i++) {
            weaponIds.add(weaponArray.getInt(i));
        }

        Set<Stratagem> stratagems = new HashSet<>(this.stratagemRepository.findAllById(stratagemIds));
        user.setStratagems(stratagems);
        user = this.userRepository.save(user);

        return ResponseEntity.ok(this.responses.userProfile(user));
    }

}
