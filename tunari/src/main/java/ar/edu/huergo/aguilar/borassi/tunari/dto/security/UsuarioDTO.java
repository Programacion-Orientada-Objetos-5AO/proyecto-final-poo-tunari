package ar.edu.huergo.aguilar.borassi.tunari.dto.security;

import java.util.List;

public record UsuarioDTO(String username, List<String> roles) {
    
}
