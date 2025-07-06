package fr.alexpado.bravediver;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SecurityScheme(
        name = "BearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "uuid",
        in = SecuritySchemeIn.HEADER
)
@SpringBootApplication
public class BravediverApplication {

    public static void main(String[] args) {

        SpringApplication.run(BravediverApplication.class, args);
    }

}
