package ar.edu.huergo.aguilar.borassi.tunari.service.agencia;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.huergo.aguilar.borassi.tunari.dto.agencia.CrearAutoStockDTO;
import ar.edu.huergo.aguilar.borassi.tunari.entity.agencia.AutoStock;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Auto;
import ar.edu.huergo.aguilar.borassi.tunari.repository.agencia.AutoStockRepository;
import ar.edu.huergo.aguilar.borassi.tunari.service.auto.AutoService;
import jakarta.persistence.EntityNotFoundException;

@Service
public class AutoStockService {
    @Autowired
    private AutoStockRepository autoStockRepository;
    @Autowired
    private AutoService autoService;


    public List<AutoStock> obtenerTodosLosAutoStock() {
        return autoStockRepository.findAll();
    }

    public AutoStock obtenerAutoStockPorId(Long id) throws EntityNotFoundException {
        return autoStockRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("AutoStock no encontrado"));
    }

    public AutoStock crearAutoStock(CrearAutoStockDTO autoStock) {
        AutoStock autoStockEntity = new AutoStock();
        Auto auto = autoService.resolverAuto(autoStock.autoId());
        autoStockEntity.setAuto(auto);
        autoStockEntity.setStock(autoStock.stock());
        return autoStockRepository.save(autoStockEntity);
    }

    public AutoStock actualizarAutoStock(Long id, CrearAutoStockDTO autoStock) throws EntityNotFoundException {
        AutoStock autoStockExistente = obtenerAutoStockPorId(id);
        Auto auto = autoService.resolverAuto(autoStock.autoId());
        autoStockExistente.setStock(autoStock.stock());
        autoStockExistente.setAuto(auto);
        return autoStockRepository.save(autoStockExistente);
    }
    
    public void eliminarAutoStock(Long id) throws EntityNotFoundException {
        AutoStock autoStock = obtenerAutoStockPorId(id);
        autoStockRepository.delete(autoStock);
    }

    public List<AutoStock> resolverAutoStock(List<Long> autoStocksIds) throws IllegalArgumentException, EntityNotFoundException {
        if (autoStocksIds == null || autoStocksIds.isEmpty()) {
            throw new IllegalArgumentException("Hay que ingresar un autoStock.");
        }
        List<AutoStock> autoStocks = autoStockRepository.findAllById(autoStocksIds);
        if (autoStocks.size() != autoStocks.stream().filter(Objects::nonNull).distinct()
                .count()) {
            throw new EntityNotFoundException("Uno o más autoStocks no existen. Intente nuevamente.");
        }
        return autoStocks;
    }
}