package duoc.dairys.pedidos.controller;

import org.springframework.web.bind.annotation.RestController;

import duoc.dairys.pedidos.DTO.PedidoDTO;
import duoc.dairys.pedidos.DTO.ResponseDTO;
import duoc.dairys.pedidos.model.Pedido;
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
@RequestMapping("api/ecomarket/v1/pedido")
public class PedidoController {
    
    @Autowired
    private PedidoServicio pedidoServicio;

    //crear pedido
    @PostMapping("/crear")
    public ResponseEntity<?> crear(@RequestBody PedidoDTO dto) {
        Pedido pedido = pedidoServicio.crearPedido(dto);
        return ResponseEntity.status(201).body(new ResponseDTO("Pedido creado correctamente", pedido));
    }

    
    //obtener pedido por id
    @GetMapping("/{id}")
    public ResponseEntity<?> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(new ResponseDTO("Pedido encontrado", pedidoServicio.obtenerPedido(id)));
    }  


    //listar pedidos
    @GetMapping
    public ResponseEntity<?> listar() {
        return ResponseEntity.ok(new ResponseDTO("Listado de pedidos", pedidoServicio.listarPedidos()));
    }

    //estado del pedido
     @PutMapping("/estado/{id}")
    public ResponseEntity<?> cambiarEstado(@PathVariable Long idPedido, @RequestParam String estado) {

        return ResponseEntity.ok(new ResponseDTO( "Estado actualizado", pedidoServicio.cambiarEstado(idPedido, estado)));
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

}
