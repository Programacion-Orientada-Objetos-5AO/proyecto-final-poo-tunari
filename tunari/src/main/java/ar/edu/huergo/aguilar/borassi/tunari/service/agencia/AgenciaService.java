package ar.edu.huergo.aguilar.borassi.tunari.service.agencia;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.huergo.aguilar.borassi.tunari.dto.agencia.CrearAgenciaDTO;
import ar.edu.huergo.aguilar.borassi.tunari.entity.agencia.Agencia;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Auto;
import ar.edu.huergo.aguilar.borassi.tunari.repository.agencia.AgenciaRepository;
import ar.edu.huergo.aguilar.borassi.tunari.service.auto.MarcaService;
import jakarta.persistence.EntityNotFoundException;

@Service
public class AgenciaService {
    @Autowired
    private AgenciaRepository agenciaRepository;
    @Autowired
    private MarcaService marcaService;


    public List<Agencia> obtenerTodosLosAgencia() {
        return agenciaRepository.findAll();
    }

    public Agencia obtenerAgenciaPorId(Long id) throws EntityNotFoundException {
        return agenciaRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Agencia no encontrado"));
    }

    public Agencia crearAgencia(CrearAgenciaDTO agencia) {
        Agencia agenciaEntity = new Agencia();
        Auto auto = marcaService.resolverMarca(agencia.marcaId());
        agenciaEntity.setAuto(auto);
        agenciaEntity.setStock(agencia.stock());
        return agenciaRepository.save(agenciaEntity);
    }

    public Agencia actualizarAgencia(Long id, CrearAgenciaDTO agencia) throws EntityNotFoundException {
        Agencia agenciaExistente = obtenerAgenciaPorId(id);
        Auto auto = autoService.resolverAuto(agencia.autoId());
        agenciaExistente.setStock(agencia.stock());
        agenciaExistente.setAuto(auto);
        return agenciaRepository.save(agenciaExistente);
    }
    
    public void eliminarAgencia(Long id) throws EntityNotFoundException {
        Agencia agencia = obtenerAgenciaPorId(id);
        agenciaRepository.delete(agencia);
    }


}