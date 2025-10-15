package ar.edu.huergo.aguilar.borassi.tunari.controller.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import ar.edu.huergo.aguilar.borassi.restaurante.entity.plato.Plato;
import ar.edu.huergo.aguilar.borassi.restaurante.entity.security.Usuario;
import ar.edu.huergo.aguilar.borassi.restaurante.entity.pedido.Pedido;
import ar.edu.huergo.aguilar.borassi.restaurante.service.plato.IngredienteService;
import ar.edu.huergo.aguilar.borassi.restaurante.service.plato.PlatoService;
import ar.edu.huergo.aguilar.borassi.restaurante.service.security.UsuarioService;
import ar.edu.huergo.aguilar.borassi.restaurante.service.pedido.PedidoService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

/**
 * Controlador web para manejar las vistas de Tunari usando Thymeleaf
 * Este controlador sirve páginas HTML en lugar de respuestas JSON
 */
@Controller
@RequestMapping("/web")
public class TunariWebController {

    /**
     * Página de login
     */
    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                       @RequestParam(value = "logout", required = false) String logout,
                       Model model) {
        if (error != null) {
            model.addAttribute("error", "Usuario o contraseña incorrectos");
        }
        if (logout != null) {
            model.addAttribute("success", "Has cerrado sesión correctamente");
        }
        return "auth/login";
    }

    /**
     * Página de registro
     */
    @GetMapping("/registro")
    public String registro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "auth/registro";
    }

    /**
     * Procesar registro de usuario
     */
    @PostMapping("/registro")
    public String procesarRegistro(@Valid @ModelAttribute Usuario usuario,
                                 BindingResult result,
                                 RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "auth/registro";
        }

        try {
            usuarioService.registrarUsuario(usuario.getUsername(), usuario.getUsername(), usuario.getPassword());
            redirectAttributes.addFlashAttribute("success", "Usuario registrado exitosamente. Puedes iniciar sesión.");
            return "redirect:/web/login";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al registrar usuario: " + e.getMessage());
            return "redirect:/web/registro";
        }
    }

    /**
     * Logout
     */
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/web/login?logout";
    }

}
