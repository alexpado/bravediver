package fr.alexpado.bravediver.web.controllers;

import fr.alexpado.bravediver.web.oauth.AuthenticationManager;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

// This will be most likely a temporary setup while testing rest endpoints.
@Controller
public class WebController {

    private final AuthenticationManager authManager;

    public WebController(AuthenticationManager authManager) {

        this.authManager = authManager;
    }

    @GetMapping
    public String index(@Header(value = "x-session", defaultValue = "") String session, Model model) {

        return "index";
    }

}
