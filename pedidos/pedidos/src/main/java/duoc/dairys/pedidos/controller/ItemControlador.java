package duoc.dairys.pedidos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import duoc.dairys.pedidos.model.ItemCarrito;
import duoc.dairys.pedidos.service.ItemServicio;

@RestController
@RequestMapping("/itemCarrito")
public class ItemControlador {

    @Autowired
    private ItemServicio itemServicio;
    
    //crear item
    @PostMapping("/crear")
    public ResponseEntity<?> crear(@RequestBody ItemCarrito item) {
        return ResponseEntity.status(201).body(itemServicio.crear(item));
    }

    //listar items
    @GetMapping
    public ResponseEntity<?> listar() {
        return ResponseEntity.ok(itemServicio.listar());
    }

    //eliminar
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        itemServicio.eliminar(id);
        return ResponseEntity.ok("Item eliminado");
    }
}

