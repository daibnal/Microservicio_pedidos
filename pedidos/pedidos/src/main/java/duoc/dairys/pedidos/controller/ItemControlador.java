package duoc.dairys.pedidos.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import duoc.dairys.pedidos.DTO.ItemCarritoDTO;
import duoc.dairys.pedidos.DTO.ResponseDTO;
import duoc.dairys.pedidos.model.ItemCarrito;
import duoc.dairys.pedidos.service.ItemServicio;

@RestController
@RequestMapping("/itemCarrito")
public class ItemControlador {

    @Autowired
    private ItemServicio itemServicio;
    
    //crear item
    @PostMapping("/crear")
    public ResponseEntity<ResponseDTO> crear(@RequestBody ItemCarritoDTO dto) {
    ItemCarrito itemCreado = itemServicio.crear(dto);

    return ResponseEntity.status(201).body(new ResponseDTO("Item creado correctamente", itemCreado) );
    }

    //listar items
    @GetMapping
    public ResponseEntity<ResponseDTO> listar() {
        List<ItemCarrito> items = itemServicio.listar();
        return ResponseEntity.ok(new ResponseDTO("Lista de items obtenida correctamente", items));
    }

    //eliminar
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<ResponseDTO> eliminar(@PathVariable Long id) {
        itemServicio.eliminar(id);
        return ResponseEntity.ok(new ResponseDTO("Item eliminado correctamente", null));
    }
}

