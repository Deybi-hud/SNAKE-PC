package SNAKE_PC.demo.service.producto;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import SNAKE_PC.demo.model.producto.Producto;
import SNAKE_PC.demo.repository.producto.ProductoRepository;

import jakarta.transaction.Transactional;

@SuppressWarnings("null")
@Service
@Transactional
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> findAll() {
        return productoRepository.findAll();
    }

    public Producto findById(long id) {
        return productoRepository.findById(id).orElse(null);
    }

    public Producto save(Producto producto) {
        return productoRepository.save(producto);
    }

    public void delete(long id) {
        productoRepository.deleteById(id);
    }

    public Producto patchProducto(Long id, Producto producto) {
        Optional<Producto> productoOptional = productoRepository.findById(id);
        if (productoOptional.isPresent()) {
            Producto productoUpdate = productoOptional.get();
            if (producto.getNombreProducto() != null) {
                productoUpdate.setNombreProducto(producto.getNombreProducto());
            }
            if (producto.getStock() != null) {
                productoUpdate.setStock(producto.getStock());
            }
            if (producto.getPrecio() != null) {
                productoUpdate.setPrecio(producto.getPrecio());
            }
            if (producto.getSku() != null) {
                productoUpdate.setSku(producto.getSku());
            }
            return productoRepository.save(productoUpdate);
        } else {
            return null;
        }
    }
}
