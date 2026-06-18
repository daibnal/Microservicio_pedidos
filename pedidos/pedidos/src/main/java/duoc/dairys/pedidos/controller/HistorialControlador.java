package duoc.dairys.pedidos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import duoc.dairys.pedidos.DTO.ResponseDTO;
import duoc.dairys.pedidos.model.HistorialPedido;
import duoc.dairys.pedidos.service.HistorialServicio;

@RestController
@RequestMapping("/historialPedidos")
public class HistorialControlador {
    
    @Autowired
    private HistorialServicio historialServicio;


    //crear historial
    @PostMapping("/crear")
    public ResponseEntity<?> crear(@RequestBody HistorialPedido historial) {
        return ResponseEntity.status(201).body(new ResponseDTO("Historial creado correctamente",historialServicio.crear(historial)));
    }

    //listar historiales
    @GetMapping
    public ResponseEntity<?> listar() {
        return ResponseEntity.ok(new ResponseDTO("Lista de historiales obtenida correctamente",historialServicio.listar()));
    }

    //obtener historial por id
    @GetMapping("/{id}")
    public ResponseEntity<?> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(new ResponseDTO("Historial encontrado", historialServicio.obtener(id)));
    }


}
