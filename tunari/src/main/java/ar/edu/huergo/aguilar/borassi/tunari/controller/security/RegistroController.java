package ar.edu.huergo.aguilar.borassi.tunari.controller.security;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import ar.edu.huergo.aguilar.borassi.tunari.entity.security.Usuario;
import ar.edu.huergo.aguilar.borassi.tunari.service.security.UsuarioService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class RegistroController {

    private final UsuarioService usuarioService;

    // GET: mostrar el formulario de registro
    @GetMapping("/register")
    public String mostrarFormulario(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "auth/registrarse"; // ahora apunta al archivo correcto
    }

    // POST: procesar el formulario de registro
    @PostMapping("/register")
    public String registrarCuenta(
            @ModelAttribute("usuario") Usuario usuario,
            @RequestParam("password2") String verificacionPassword,
            Model model) {

        try {
            usuarioService.registrar(usuario, usuario.getPassword(), verificacionPassword);
            model.addAttribute("exito", "Usuario registrado correctamente. Ahora podés iniciar sesión.");
            return "auth/iniciar-sesion"; // apunta al archivo correcto
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "auth/registrarse"; // apunta al archivo correcto
        }
    }
}