package SNAKE_PC.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import SNAKE_PC.demo.model.producto.Producto;
import SNAKE_PC.demo.service.producto.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("api/v1/productos")
@Tag(name = "Productos", description = "Aquí estan los productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    @Operation(summary = "Esta api llama a todos los productos", description = "Esta api se encarga de obtener todos los productos")
    public ResponseEntity<List<Producto>> listar() {
        List<Producto> productos = productoService.findAll();
        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Esta api llama a un productos por su id", description = "esta api se encarga de obtener a un productos por id")
    public ResponseEntity<Producto> buscar(@PathVariable long id) {
        try {
            Producto producto = productoService.findById(id);
            return ResponseEntity.ok(producto);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/agregar")
    @Operation(summary = "Esta api actualiza un producto", description = "esta api se encarga de actualizar a un producto existente")
    public ResponseEntity<?> agregar(@RequestBody Producto producto) {
        try {
            Producto nuevoProducto = productoService.save(producto);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProducto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se puedo guardar");
        }
    }

    @PostMapping("/{id}")
    @Operation(summary = "Esta api actualiza un producto", description = "esta api se encarga de actualizar a un producto existente")
    public ResponseEntity<Producto> updateProducto(@PathVariable Long id, @RequestBody Producto producto) {
        Producto updateProducto = productoService.save(producto);
        if (updateProducto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updateProducto);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Esta api actualiza parcialmente un producto", description = "esta api se encarga de actualizar parcialmente a un producto existente")
    public ResponseEntity<Producto> patchProducto(@PathVariable Long id, @RequestBody Producto producto) {
        try {
            Producto updateProducto = productoService.patchProducto(id, producto);
            return ResponseEntity.ok(updateProducto);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Esta api elimina a un producto", description = "esta api se encarga de eliminar a un producto existente")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        try{
            productoService.delete(id);
            return ResponseEntity.ok("Se a eliminado");
        }catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }
}
