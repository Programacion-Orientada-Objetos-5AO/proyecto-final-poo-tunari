package ar.edu.huergo.aguilar.borassi.tunari.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import ar.edu.huergo.aguilar.borassi.tunari.entity.security.Usuario;
import ar.edu.huergo.aguilar.borassi.tunari.service.security.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Controller
@RequestMapping("")
public class TunariWebController {

    @Autowired
    private UsuarioService usuarioService;

    // 🏠 Página principal
    @GetMapping({"/", "/inicio"})
    public String mostrarInicio() {
        return "index";
    }

    // 🟢 Login
    @GetMapping({"/auth/iniciar-sesion", "/iniciar-sesion"})
    public String mostrarIniciarSesion(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "exito", required = false) String exito,
            Model model) {

        if (error != null) {
            model.addAttribute("error", "Usuario o contraseña incorrectos");
        }

        if (exito != null) {
            model.addAttribute("exito", "Cuenta creada correctamente. Ya podés iniciar sesión.");
        }

        return "auth/iniciar-sesion";
    }

    // ===== REGISTRO =====

    // Mostrar formulario
    @GetMapping({"/auth/register", "/auth/registrarse"})
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "auth/registrarse";
    }

    // Procesar registro
    @PostMapping("/auth/register")
    public String registrarCuenta(
            @Valid @ModelAttribute("usuario") Usuario usuario,
            BindingResult result,
            @RequestParam("password2") String password2,
            Model model) {

        // Validación de campos
        if (result.hasErrors()) {
            model.addAttribute("error", "Por favor, revisá los campos e intentá nuevamente.");
            return "auth/registrarse";
        }

        String password = usuario.getPassword();

        if (password == null || password.isBlank()) {
            model.addAttribute("error", "La contraseña es requerida.");
            return "auth/registrarse";
        }

        // Reglas de seguridad de contraseña
        if (!password.matches("^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{16,}$")) {
            model.addAttribute("error",
                "La contraseña debe tener al menos 16 caracteres, una mayúscula, un número y un símbolo.");
            return "auth/registrarse";
        }

        if (!password.equals(password2)) {
            model.addAttribute("error", "Las contraseñas no coinciden.");
            return "auth/registrarse";
        }

        // Intento de registro
        try {
            usuarioService.registrar(usuario);
            // ✅ Redirige al login con mensaje de éxito
            return "redirect:/iniciar-sesion?exito=1";
        } catch (Exception e) {
            model.addAttribute("error", "El usuario ya existe o hubo un error al registrar la cuenta.");
            return "auth/registrarse";
        }
    }

    // 🔴 Logout
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/iniciar-sesion?logout";
    }
}