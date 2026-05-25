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

    //crear historial pedidos
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody HistorialPedido historial) {
        return ResponseEntity.status(201).body(historialServicio.crearHistorial(historial));
    }

    //agregar pedido al historial
    @PostMapping("/{idHistorial}/pedido/{idPedido}")
    public ResponseEntity<?> agregarPedido(@PathVariable Long idHistorial, @PathVariable Long idPedido) {
        return ResponseEntity.ok(new ResponseDTO("Pedido agregado al historial", historialServicio.agregarPedido(idHistorial, idPedido)));
    }

    //obtener pedidos
    @GetMapping("/{idHistorial}/pedidos")
    public ResponseEntity<?> obtenerPedidos(@PathVariable Long idHistorial) {
        return ResponseEntity.ok(new ResponseDTO("Pedidos obtenidos correctamente",historialServicio.obtenerPedidos(idHistorial)));
    }


}
