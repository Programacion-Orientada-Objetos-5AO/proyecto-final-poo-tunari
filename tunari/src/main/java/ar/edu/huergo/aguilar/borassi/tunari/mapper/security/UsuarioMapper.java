package ar.edu.huergo.aguilar.borassi.tunari.mapper.security;

import java.util.List;

import org.springframework.stereotype.Component;

import ar.edu.huergo.aguilar.borassi.tunari.dto.security.MostrarUsuarioPedidoDTO;
import ar.edu.huergo.aguilar.borassi.tunari.dto.security.RegistrarDTO;
import ar.edu.huergo.aguilar.borassi.tunari.dto.security.UsuarioDTO;
import ar.edu.huergo.aguilar.borassi.tunari.entity.security.Rol;
import ar.edu.huergo.aguilar.borassi.tunari.entity.security.Usuario;

@Component
public class UsuarioMapper {
    public UsuarioDTO toDTO(Usuario usuario) {
        if (usuario == null) {
            return null;
        }
        return new UsuarioDTO(usuario.getUsername(), usuario.getRoles().stream()
                .map(Rol::getNombre)
                .toList());
    }

    public List<UsuarioDTO> toDTOList(List<Usuario> usuarios) {
        return usuarios.stream()
                .map(this::toDTO)
                .toList();
    }

    public Usuario toEntity(RegistrarDTO registrarDTO) {
        if (registrarDTO == null) {
            return null;
        }
        Usuario usuario = new Usuario();
        usuario.setUsername(registrarDTO.username());
        return usuario;
    }

    public MostrarUsuarioPedidoDTO toDTOPedido(Usuario usuario) {
        if (usuario == null) {
            return null;
        }
        return new MostrarUsuarioPedidoDTO(usuario.getUsername());
    }
}
