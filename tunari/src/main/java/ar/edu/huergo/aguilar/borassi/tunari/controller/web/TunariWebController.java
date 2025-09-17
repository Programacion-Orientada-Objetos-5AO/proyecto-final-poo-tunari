package ar.edu.huergo.aguilar.borassi.tunari.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TunariWebController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("titulo", "Tunari");
        model.addAttribute("mensaje", "Bienvenido a Tunari!");
        return "index"; // templates/index.html
    }

    @GetMapping("/login")
    public String login() {
        return "login"; // templates/login.html (opcional)
    }
}
