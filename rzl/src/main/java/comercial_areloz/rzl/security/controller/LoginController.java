package comercial_areloz.rzl.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/auth/login")
    public String login() {
        return "/auth/login"; // Esto busca login.html en /templates
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

}
