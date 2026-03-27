package ar.edu.huergo.aguilar.borassi.tunari.service.agencia;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.huergo.aguilar.borassi.tunari.dto.agencia.CrearAgenciaDTO;
import ar.edu.huergo.aguilar.borassi.tunari.dto.agencia.ModificarStockAgenciaDTO;
import ar.edu.huergo.aguilar.borassi.tunari.entity.agencia.Agencia;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Marca;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Vehiculo;
import ar.edu.huergo.aguilar.borassi.tunari.repository.agencia.AgenciaRepository;
import ar.edu.huergo.aguilar.borassi.tunari.service.auto.MarcaService;
import ar.edu.huergo.aguilar.borassi.tunari.service.auto.VehiculoService;
import jakarta.persistence.EntityNotFoundException;

@Service
public class AgenciaService {
    @Autowired
    private AgenciaRepository agenciaRepository;
    @Autowired
    private MarcaService marcaService;
    @Autowired
    private VehiculoService vehiculoService;


    public List<Agencia> obtenerTodasLasAgencias() {
        return agenciaRepository.findAll();
    }

    public Agencia obtenerAgenciaPorId(Long id) throws EntityNotFoundException {
        return agenciaRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Agencia no encontrado"));
    }

    public Agencia crearAgencia(Agencia agencia, Long marcaId) {
        Marca marca = marcaService.obtenerMarcaPorId(marcaId);
        agencia.setMarca(marca);
        return agenciaRepository.save(agencia);
    }

    public Agencia actualizarAgencia(Long id, Agencia agencia, Long marcaId) throws EntityNotFoundException {
        Agencia agenciaExistente = obtenerAgenciaPorId(id);
        agenciaExistente.setNombre(agencia.getNombre());
        Marca marca = marcaService.obtenerMarcaPorId(marcaId);
        agenciaExistente.setMarca(marca);
        agenciaExistente.setUbicacion(agencia.getUbicacion());
        return agenciaRepository.save(agenciaExistente);
    }
    
    public void eliminarAgencia(Long id) throws EntityNotFoundException {
        Agencia agencia = obtenerAgenciaPorId(id);
        agenciaRepository.delete(agencia);
    }

    public Agencia actualizarStock(Long idAgencia, Long idAuto, int nuevoStock) throws EntityNotFoundException{
        Agencia agenciaExistente = obtenerAgenciaPorId(idAgencia);
        Vehiculo auto = vehiculoService.resolverVehiculo(idAuto);
        agenciaExistente.modificarStock(auto, nuevoStock);
        return agenciaRepository.save(agenciaExistente);
    }

}