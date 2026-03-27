package ar.edu.huergo.aguilar.borassi.tunari.service.auto;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.huergo.aguilar.borassi.tunari.dto.auto.TipoVehiculo;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Auto;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Color;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Marca;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Modelo;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.PickUp;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Vehiculo;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Version;
import ar.edu.huergo.aguilar.borassi.tunari.repository.auto.VehiculoRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class VehiculoService {

    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Autowired
    private ModeloService modeloService;

    @Autowired
    private ColorService colorService;

    @Autowired
    private VersionService versionService;

    @Autowired
    private MarcaService marcaService;

    public List<Vehiculo> obtenerTodosLosVehiculos() {
        return vehiculoRepository.findAll();
    }

    public Vehiculo obtenerVehiculoPorId(Long id) throws EntityNotFoundException {
        return vehiculoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Vehículo no encontrado."));
    }

    public Vehiculo crearVehiculo(Vehiculo vehiculo, Long marcaId, Long modeloId, Long colorId, Long versionId, TipoVehiculo tipo, Integer medicionBaul) {
        Marca marca = marcaService.obtenerMarcaPorId(marcaId);
        Modelo modelo = modeloService.obtenerModeloPorId(modeloId);
        Color color = colorService.obtenerColorPorId(colorId);
        Version version = versionService.obtenerVersionPorId(versionId);
        modelo.validarVehiculo(color, version);
            

        if (tipo.equals("PICKUP")) {
            PickUp autoCreado = new PickUp();
            autoCreado.setPesoBaul(medicionBaul);
            autoCreado.setMarca(marca);
            autoCreado.setModelo(modelo);
            autoCreado.setColor(color);
            autoCreado.setVersion(version);
            return vehiculoRepository.save(autoCreado);
            
        }else {
            Auto autoCreado = new Auto();
            autoCreado.setMarca(marca);
            autoCreado.setModelo(modelo);
            autoCreado.setColor(color);
            autoCreado.setVersion(version);
            autoCreado.setTamanoBaul(medicionBaul);
            return vehiculoRepository.save(autoCreado);
        }
    

        
    }

    public Vehiculo actualizarVehiculo(Long id, Vehiculo datosActualizados, 
                                       Long marcaId, Long modeloId, Long colorId, Long versionId) {
        Vehiculo existente = obtenerVehiculoPorId(id);

        Marca marca = marcaService.obtenerMarcaPorId(marcaId);
        Modelo modelo = modeloService.obtenerModeloPorId(modeloId);
        Color color = colorService.obtenerColorPorId(colorId);
        Version version = versionService.obtenerVersionPorId(versionId);

        modelo.validarVehiculo(color, version);

        existente.setMarca(marca);
        existente.setModelo(modelo);
        existente.setColor(color);
        existente.setVersion(version);

        return vehiculoRepository.save(existente);
    }

    public void eliminarVehiculo(Long id) throws EntityNotFoundException {
        Vehiculo vehiculo = obtenerVehiculoPorId(id);
        vehiculoRepository.delete(vehiculo);
    }

    public Vehiculo resolverVehiculo(Long vehiculoId) throws EntityNotFoundException {
        return vehiculoRepository.findById(vehiculoId)
                .orElseThrow(() -> new EntityNotFoundException("Vehículo no encontrado."));
    }
}
