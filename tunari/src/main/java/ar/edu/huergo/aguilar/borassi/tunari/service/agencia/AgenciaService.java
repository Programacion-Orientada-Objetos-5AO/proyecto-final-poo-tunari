package ar.edu.huergo.aguilar.borassi.tunari.service.agencia;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.huergo.aguilar.borassi.tunari.dto.agencia.ModificarStockAgenciaDTO;
import ar.edu.huergo.aguilar.borassi.tunari.dto.agencia.CrearAgenciaDTO;
import ar.edu.huergo.aguilar.borassi.tunari.entity.agencia.Agencia;
import ar.edu.huergo.aguilar.borassi.tunari.entity.agencia.AutoStock;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Auto;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Marca;
import ar.edu.huergo.aguilar.borassi.tunari.repository.agencia.AgenciaRepository;
import ar.edu.huergo.aguilar.borassi.tunari.service.auto.AutoService;
import ar.edu.huergo.aguilar.borassi.tunari.service.auto.MarcaService;
import jakarta.persistence.EntityNotFoundException;

@Service
public class AgenciaService {
    @Autowired
    private AgenciaRepository agenciaRepository;
    @Autowired
    private MarcaService marcaService;
    @Autowired
    private AutoService autoService;


    public List<Agencia> obtenerTodosLosAgencia() {
        return agenciaRepository.findAll();
    }

    public Agencia obtenerAgenciaPorId(Long id) throws EntityNotFoundException {
        return agenciaRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Agencia no encontrado"));
    }

    public Agencia crearAgencia(CrearAgenciaDTO agencia) {
        Agencia agenciaEntity = new Agencia();
        agenciaEntity.setNombre(agencia.nombre());
        Marca marca = marcaService.obtenerMarcaPorId(agencia.marcaId());
        agenciaEntity.setMarca(marca);
        agenciaEntity.setUbicacion(agencia.ubicacion());
        return agenciaRepository.save(agenciaEntity);
    }

    public Agencia actualizarAgencia(Long id, CrearAgenciaDTO agencia) throws EntityNotFoundException {
        Agencia agenciaExistente = obtenerAgenciaPorId(id);
        agenciaExistente.setNombre(agencia.nombre());
        Marca marca = marcaService.obtenerMarcaPorId(agencia.marcaId());
        agenciaExistente.setMarca(marca);
        agenciaExistente.setUbicacion(agencia.ubicacion());
        return agenciaRepository.save(agenciaExistente);
    }
    
    public void eliminarAgencia(Long id) throws EntityNotFoundException {
        Agencia agencia = obtenerAgenciaPorId(id);
        agenciaRepository.delete(agencia);
    }

    public Agencia actualizarStock(ModificarStockAgenciaDTO autoStock) throws EntityNotFoundException{
        Agencia agenciaExistente = obtenerAgenciaPorId(autoStock.idAgencia());
        Auto auto = autoService.resolverAuto(autoStock.idAuto());
        agenciaExistente.modificarStock(auto, autoStock.nuevoStock());
        return agenciaRepository.save(agenciaExistente);
    }
}