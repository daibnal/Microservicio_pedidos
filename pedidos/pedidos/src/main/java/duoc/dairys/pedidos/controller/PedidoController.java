package duoc.dairys.pedidos.controller;

import org.springframework.web.bind.annotation.RestController;

import duoc.dairys.pedidos.DTO.PedidoDTO;
import duoc.dairys.pedidos.service.PedidoServicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("api/pedido")
public class PedidoController {
    
    @Autowired
    private PedidoServicio pedidoServicio;

    //crear pedido
    @PostMapping("/crear")
    public ResponseEntity<?> crearPedido(@RequestBody PedidoDTO dto) {
        return ResponseEntity.status(201).body(pedidoServicio.crearPedido(dto));
    }

    //obtener pedidos
    @GetMapping
    public ResponseEntity<?> obtenerPedidos() {
        return ResponseEntity.ok(pedidoServicio.obtenerPedidos());
    }

    //cancelar pedido
    @PutMapping("/cancelar/{id}")
    public ResponseEntity<?> cancelarPedido(@PathVariable Long id) {
        boolean cancelado = pedidoServicio.cancelarPedido(id);

        if (!cancelado) {
            return ResponseEntity.status(404).body("Pedido no encontrado");
        }
        return ResponseEntity.ok("Pedido cancelado correctamente");
    }

    //cambiar estado del pedido
    @PutMapping("/estado/{id}")
    public ResponseEntity<?> cambiarEstado(@PathVariable Long id, @RequestParam String estado) {
        boolean actualizado = pedidoServicio.cambiarEstado(id, estado);

        if (!actualizado) {
            return ResponseEntity.status(404).body("Pedido no encontrado");
        }
        return ResponseEntity.ok("Estado actualizado correctamente");
    }

    //calcular subtotal + iva
    @GetMapping("/total")
    public ResponseEntity<?> calcularTotal(@RequestParam Double subtotal, @RequestParam Double iva) {
        return ResponseEntity.ok(pedidoServicio.calcularTotal(subtotal, iva));
    }






}
