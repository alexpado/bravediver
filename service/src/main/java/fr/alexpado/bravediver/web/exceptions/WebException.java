package fr.alexpado.bravediver.web.exceptions;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

public final class WebException extends RuntimeException {

    public static final int AUTHORIZATION_MISSING       = 10000; // Authorization header was not provided.
    public static final int AUTHORIZATION_SYNTAX        = 10001; // Authorization header was not a Bearer.
    public static final int AUTHORIZATION_UUID_FORMAT   = 10002; // Authorization was not a UUID.
    public static final int AUTHORIZATION_NO_SESSION    = 10003; // Authorization UUID did not match an existing session.
    public static final int AUTHENTICATION_FAILURE      = 10010; // Unable to exchange the code for a token.
    public static final int PROFILE_STRATAGEMS_MATCHING = 10020; // One or more IDs were not found in database.

    private static final Map<Integer, HttpStatus> errorStatusMap = new HashMap<>() {{
        this.put(AUTHORIZATION_MISSING, HttpStatus.BAD_REQUEST);
        this.put(AUTHORIZATION_SYNTAX, HttpStatus.BAD_REQUEST);
        this.put(AUTHORIZATION_UUID_FORMAT, HttpStatus.BAD_REQUEST);
        this.put(AUTHORIZATION_NO_SESSION, HttpStatus.UNAUTHORIZED);
        this.put(PROFILE_STRATAGEMS_MATCHING, HttpStatus.BAD_REQUEST);
    }};

    public static WebException of(int code) {

        HttpStatus httpStatus = errorStatusMap.getOrDefault(code, HttpStatus.INTERNAL_SERVER_ERROR);
        return new WebException(httpStatus, code);
    }

    public static WebException of(int code, Throwable cause) {

        HttpStatus httpStatus = errorStatusMap.getOrDefault(code, HttpStatus.INTERNAL_SERVER_ERROR);
        return new WebException(cause, httpStatus, code);
    }

    @Schema(name = "WebException", description = "Used when something isn't going how it is supposed to go.")
    public record Dto(
            @Schema(description = "Time at which the error has been generated.")
            ZonedDateTime timestamp,
            @Schema(description = "Unique code for this error (one error code per reason).", minimum = "10000", maximum = "19999")
            int code,
            @Schema(description = "Status code for the error.", minimum = "100", maximum = "511")
            int status,
            @Schema(description = "Human-readable name for the status code.")
            String error
    ) {

        public static Dto from(WebException ex) {

            return new Dto(ex.timestamp, ex.code, ex.status, ex.error);
        }
    }

    public final ZonedDateTime timestamp;
    public final int           code;
    public final int           status;
    public final String        error;

    private WebException(HttpStatus status, int code) {

        this.timestamp = ZonedDateTime.now();
        this.code      = code;
        this.status    = status.value();
        this.error     = status.getReasonPhrase();
    }

    private WebException(Throwable cause, HttpStatus status, int code) {

        super(cause);
        this.timestamp = ZonedDateTime.now();
        this.code      = code;
        this.status    = status.value();
        this.error     = status.getReasonPhrase();
    }

}
