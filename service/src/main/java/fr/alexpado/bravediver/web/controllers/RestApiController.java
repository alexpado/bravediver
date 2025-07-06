package fr.alexpado.bravediver.web.controllers;

import fr.alexpado.bravediver.entities.Stratagem;
import fr.alexpado.bravediver.entities.User;
import fr.alexpado.bravediver.repositories.StratagemRepository;
import fr.alexpado.bravediver.repositories.UserRepository;
import fr.alexpado.bravediver.web.dto.AuthSuccess;
import fr.alexpado.bravediver.web.dto.PostProfileDto;
import fr.alexpado.bravediver.web.dto.ProfileDto;
import fr.alexpado.bravediver.web.dto.stratagems.StratagemDto;
import fr.alexpado.bravediver.web.exceptions.WebException;
import fr.alexpado.bravediver.web.oauth.AuthenticationManager;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class RestApiController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestApiController.class);

    private final AuthenticationManager authManager;
    private final StratagemRepository   stratagemRepository;
    private final UserRepository        userRepository;

    public RestApiController(AuthenticationManager authManager, StratagemRepository stratagemRepository, UserRepository userRepository) {

        this.authManager         = authManager;
        this.stratagemRepository = stratagemRepository;
        this.userRepository      = userRepository;
    }

    private User getUser(String authorization) {

        if (authorization == null) {
            throw WebException.of(WebException.AUTHORIZATION_MISSING);
        }
        if (!authorization.startsWith("Bearer ")) {
            throw WebException.of(WebException.AUTHORIZATION_SYNTAX);
        }

        UUID uuid;

        try {
            uuid = UUID.fromString(authorization.substring("Bearer ".length()));
        } catch (Exception e) {
            throw WebException.of(WebException.AUTHORIZATION_UUID_FORMAT, e);
        }

        return this.authManager.getUserFromSession(uuid)
                               .orElseThrow(() -> WebException.of(WebException.AUTHORIZATION_NO_SESSION));
    }

    @GetMapping(value = "/auth/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Authenticate with Discord", description = "Use the code provided to try and authenticate with Discord servers.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Authentication successful.", content = @Content(schema = @Schema(implementation = AuthSuccess.class))),
            @ApiResponse(responseCode = "500", description = "Authentication failure.", content = @Content(schema = @Schema(implementation = WebException.Dto.class)))
    })
    public ResponseEntity<?> authenticate(
            @Parameter(
                    name = "code",
                    description = "The auth code given by the Discord OAuth page on the redirect uri.",
                    required = true,
                    example = "NhhvTDYsFcdgNLnnLijcl7Ku7bEEeee"
            ) @PathVariable String code) {

        try {
            UUID session = this.authManager.authenticate(code);
            return ResponseEntity.ok(new AuthSuccess(session.toString()));
        } catch (Exception e) {
            throw WebException.of(WebException.AUTHENTICATION_FAILURE, e);
        }
    }

    @GetMapping(value = "/data", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get all stratagems", description = "Retrieve a list of all available stratagems")
    @ApiResponse(responseCode = "200", description = "Successful")
    public ResponseEntity<List<StratagemDto>> applicationData() {

        return ResponseEntity.ok(this.stratagemRepository
                                         .findAll()
                                         .stream()
                                         .map(StratagemDto::from)
                                         .toList());
    }

    @GetMapping(value = "/me", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get user data", description = "Retrieve the current preferences / unlocks of the logged in user.", security = @SecurityRequirement(name = "BearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "The data has been successfully retrieved.", content = @Content(schema = @Schema(implementation = ProfileDto.class))),
            @ApiResponse(responseCode = "400", description = "The authorization header was incorrectly formatted.", content = @Content(schema = @Schema(implementation = WebException.Dto.class))),
            @ApiResponse(responseCode = "401", description = "The authorization header is not associated to a session (can happen after a server restart).", content = @Content(schema = @Schema(implementation = WebException.Dto.class))),
    })
    public ResponseEntity<ProfileDto> userData(@RequestHeader(name = "Authorization") String authorization) {

        User user = this.getUser(authorization);
        return ResponseEntity.ok(ProfileDto.from(user));
    }

    @PostMapping(value = "/me", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Set user data", description = "Define the current preferences / unlocks of the logged in user.", security = @SecurityRequirement(name = "BearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "The data has been successfully updated.", content = @Content(schema = @Schema(implementation = ProfileDto.class))),
            @ApiResponse(responseCode = "400", description = "The authorization header was incorrectly formatted.", content = @Content(schema = @Schema(implementation = WebException.Dto.class))),
            @ApiResponse(responseCode = "400", description = "One or more stratagem ids were not recognized by the service", content = @Content(schema = @Schema(implementation = WebException.Dto.class))),
            @ApiResponse(responseCode = "401", description = "The authorization header is not associated to a session (can happen after a server restart).", content = @Content(schema = @Schema(implementation = WebException.Dto.class)))
    })
    public ResponseEntity<ProfileDto> userData(@RequestHeader("Authorization") String authorization, @RequestBody PostProfileDto body) {

        User user = this.getUser(authorization);

        Set<Stratagem> stratagems = new HashSet<>(this.stratagemRepository.findAllById(body.stratagems()));

        if (stratagems.size() != body.stratagems().size()) {
            throw WebException.of(WebException.PROFILE_STRATAGEMS_MATCHING);
        }

        user.setStratagems(stratagems);
        user = this.userRepository.save(user);
        return ResponseEntity.ok(ProfileDto.from(user));
    }

}
