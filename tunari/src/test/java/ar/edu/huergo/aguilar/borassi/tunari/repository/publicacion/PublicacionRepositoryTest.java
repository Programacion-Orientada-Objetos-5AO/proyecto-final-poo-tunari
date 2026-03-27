package ar.edu.huergo.aguilar.borassi.tunari.repository.publicacion;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Marca;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Modelo;
import ar.edu.huergo.aguilar.borassi.tunari.entity.publicacion.Publicacion;
import ar.edu.huergo.aguilar.borassi.tunari.entity.security.Usuario;

@DataJpaTest
@DisplayName("Tests de Integración - PublicacionRepository")
class PublicacionRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PublicacionRepository publicacionRepository;

    private Marca marca;
    private Modelo modelo;
    private Usuario usuarioBase;
    private Publicacion pub1;
    private Publicacion pub2;

    @BeforeEach
    void setUp() {
        // Crear marca base
        marca = new Marca();
        marca.setNombre("Toyota");
        marca = entityManager.persistAndFlush(marca);

        // Crear modelo base
        modelo = new Modelo();
        modelo.setNombre("Corolla 2025");
        modelo.setMarca(marca);
        modelo = entityManager.persistAndFlush(modelo);

        // Crear usuario base
        usuarioBase = new Usuario();
        usuarioBase.setUsername("facubora@gmail.com");
        usuarioBase.setPassword("111111111Hola!111111111");
        usuarioBase = entityManager.persistAndFlush(usuarioBase);

        // Publicaciones iniciales
        pub1 = new Publicacion();
        pub1.setModelo(modelo);
        pub1.setUsuario(usuarioBase);
        pub1 = entityManager.persistAndFlush(pub1);

        pub2 = new Publicacion();
        pub2.setModelo(modelo);
        pub2.setUsuario(usuarioBase);
        pub2 = entityManager.persistAndFlush(pub2);

        entityManager.clear();
    }

    @Test
    @DisplayName("Debería guardar y recuperar una publicación correctamente")
    void deberiaGuardarYRecuperarPublicacion() {
        Publicacion nueva = new Publicacion();
        nueva.setModelo(modelo);
        nueva.setUsuario(usuarioBase);
        Publicacion guardada = publicacionRepository.save(nueva);

        entityManager.flush();
        entityManager.clear();

        Optional<Publicacion> recuperada = publicacionRepository.findById(guardada.getId());
        assertTrue(recuperada.isPresent());
        assertEquals(modelo.getNombre(), recuperada.get().getModelo().getNombre());
        assertEquals(usuarioBase.getUsername(), recuperada.get().getUsuario().getUsername());
    }

    @Test
    @DisplayName("Debería obtener todas las publicaciones")
    void deberiaObtenerTodasLasPublicaciones() {
        List<Publicacion> publicaciones = publicacionRepository.findAll();

        assertNotNull(publicaciones);
        assertEquals(2, publicaciones.size());
        assertTrue(publicaciones.stream()
                .allMatch(p -> p.getModelo().getNombre().equals("Corolla 2025")));
    }

    @Test
    @DisplayName("Debería eliminar una publicación correctamente")
    void deberiaEliminarPublicacion() {
        Long id = pub1.getId();
        assertTrue(publicacionRepository.existsById(id));

        publicacionRepository.deleteById(id);
        entityManager.flush();

        assertFalse(publicacionRepository.existsById(id));
    }

    @Test
    @DisplayName("Debería lanzar excepción si se intenta guardar sin modelo o sin usuario")
    void deberiaLanzarExcepcionSiFaltaModeloOUsuario() {
        Publicacion sinModelo = new Publicacion();
        sinModelo.setUsuario(usuarioBase);

        Publicacion sinUsuario = new Publicacion();
        sinUsuario.setModelo(modelo);

    }
}
