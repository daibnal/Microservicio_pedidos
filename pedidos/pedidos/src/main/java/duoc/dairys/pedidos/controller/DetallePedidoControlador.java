package duoc.dairys.pedidos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import duoc.dairys.pedidos.DTO.ResponseDTO;
import duoc.dairys.pedidos.service.DetallePedidoServicio;

@RestController
@RequestMapping("/detalle")
public class DetallePedidoControlador {

    @Autowired
    private DetallePedidoServicio detallePedidoServicio;

    //listar detalles
    @GetMapping
    public ResponseEntity<?> listarDetalles() {
        return ResponseEntity.ok(new ResponseDTO("Lista de detalles obtenida correctamente", detallePedidoServicio.listar()));
    }

    //obtener detalle por id
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerDetalle(@PathVariable Long id) {
        return ResponseEntity.ok(new ResponseDTO("Detalle encontrado", detallePedidoServicio.obtener(id)));
    }

    //eliminar detalle
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarDetalle(@PathVariable Long id) {

        detallePedidoServicio.eliminar(id);
        return ResponseEntity.ok(new ResponseDTO("Detalle eliminado correctamente", null));
    }

}
