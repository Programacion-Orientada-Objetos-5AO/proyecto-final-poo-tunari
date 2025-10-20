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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import ar.edu.huergo.aguilar.borassi.tunari.entity.security.Usuario;
import ar.edu.huergo.aguilar.borassi.tunari.service.security.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

/**
 * Controlador web para manejar las vistas HTML (Thymeleaf) de Tunari
 */
@Controller
@RequestMapping("")
public class TunariWebController {

    @Autowired
    private UsuarioService usuarioService;

    // 🟣 Página de inicio (opcional)
    @GetMapping("/inicio")
    public String inicio() {
        return "index"; // busca templates/index.html
    }

    // 🟢 Página de login
    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                       Model model) {
        if (error != null) {
            model.addAttribute("error", "Usuario o contraseña incorrectos");
        }
        return "auth/iniciar-sesion"; // busca templates/auth/iniciar-sesion.html
    }

    @GetMapping("/auth/registrarse")
    public String mostrarFormulario(Model model) {
        model.addAttribute("usuario", new Usuario()); 
        return "auth/registrarse";
    }

    // 🟡 Procesar el formulario de registro
    @PostMapping("/registro")
    public String procesarRegistro(@Valid @ModelAttribute Usuario usuario,
                                   BindingResult result,
                                   RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "auth/registrarse";
        }

        try {
            usuarioService.registrar(usuario, usuario.getUsername(), usuario.getPassword());
            redirectAttributes.addFlashAttribute("success", 
                "Usuario registrado exitosamente. Podés iniciar sesión.");
            return "redirect:/iniciar-sesion";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", 
                "Error al registrar usuario: " + e.getMessage());
            return "redirect:/registrarse";
        }
    }

    // 🔴 Logout
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/web/login?logout";
    }
}
