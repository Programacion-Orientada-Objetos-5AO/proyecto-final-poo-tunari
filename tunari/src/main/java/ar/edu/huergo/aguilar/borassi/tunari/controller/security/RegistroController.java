package ar.edu.huergo.aguilar.borassi.tunari.controller.security;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import ar.edu.huergo.aguilar.borassi.tunari.entity.security.Usuario;
import ar.edu.huergo.aguilar.borassi.tunari.service.security.UsuarioService;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class RegistroController {

    private final UsuarioService usuarioService;

    //  GET: mostrar formulario
    @GetMapping("/register")
    public String mostrarFormulario(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "auth/registrarse";
    }

    //  POST: procesar formulario
    @PostMapping("/register")
    public String registrarCuenta(
            @Valid @ModelAttribute("usuario") Usuario usuario,
            @RequestParam("password2") String verificacionPassword,
            BindingResult result,
            Model model) {

        //  Validaciones de Bean Validation (@Email, @NotBlank, etc.)
        if (result.hasErrors()) {
            model.addAttribute("error", "Por favor revisá los campos marcados. El correo debe ser válido y todos los campos requeridos.");
            return "auth/registrarse";
        }

        try {
            usuarioService.registrar(usuario, usuario.getPassword(), verificacionPassword);
            model.addAttribute("exito", "Usuario registrado correctamente. Ahora podés iniciar sesión.");
            return "auth/iniciar-sesion";
        } catch (ConstraintViolationException e) {
            model.addAttribute("error", "Los datos ingresados no son válidos. Verificá tu email y contraseña.");
            return "auth/registrarse";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage()); // por ejemplo “Las contraseñas no coinciden”
            return "auth/registrarse";
        } catch (Exception e) {
            model.addAttribute("error", "Ocurrió un error inesperado. Intentá nuevamente.");
            return "auth/registrarse";
        }
    }
}