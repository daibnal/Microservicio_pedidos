package duoc.dairys.pedidos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import duoc.dairys.pedidos.model.DetallePedido;
import duoc.dairys.pedidos.service.DetallePedidoServicio;

@RestController
@RequestMapping("/detalle")
public class DetallePedidoControlador {
    @Autowired
    private DetallePedidoServicio detallePedidoServicio;

    //crear detalle y calcular total
    @PostMapping("/crear")
    public ResponseEntity<?> crearDetalle(@RequestBody DetallePedido detalle) {
        return ResponseEntity.status(201).body(detallePedidoServicio.crearDetalle(detalle));
    }

    //listar detalle del pedido
    @GetMapping
    public ResponseEntity<?> obtenerDetalles() {
        return ResponseEntity.ok(detallePedidoServicio.obtenerDetalles());
    }
}
