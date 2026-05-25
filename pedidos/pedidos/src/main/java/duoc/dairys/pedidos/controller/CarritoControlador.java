package duoc.dairys.pedidos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import duoc.dairys.pedidos.DTO.ItemCarritoDTO;
import duoc.dairys.pedidos.model.Carrito;
import duoc.dairys.pedidos.service.CarritoServicio;

@RestController
@RequestMapping("/api/carrito")
public class CarritoControlador {
    @Autowired
    private CarritoServicio carritoServicio;


    //ver carrito por id
    @GetMapping("/ver/{idCarrito}")
    public ResponseEntity<?> obtenerCarrito(@PathVariable Long idCarrito) {
        return ResponseEntity.ok(carritoServicio.obtenerCarrito(idCarrito));
    }

    //listar carritos
    @GetMapping("verCarritos")
    public ResponseEntity<?> listar() {
        return ResponseEntity.ok(carritoServicio.listarCarritos());
    }

    //crear carrito
    @PostMapping("/crear")
    public ResponseEntity<?> crear(@RequestBody Carrito carrito) {
        return ResponseEntity.status(201).body(carritoServicio.crearCarrito(carrito));
    }


    //agregar item al carrito
    @PostMapping("/{idCarrito}/item")
    public ResponseEntity<?> agregarItem(@PathVariable Long idCarrito, @RequestBody ItemCarritoDTO dto) {
        carritoServicio.agregarItem(idCarrito, dto);
        return ResponseEntity.ok("Item agregado al carrito correctamente");
    }


    //eliminar item al carrito
    @DeleteMapping("/eliminar/{idItem}")
    public ResponseEntity<?> eliminar(@PathVariable Long idItem) {
        carritoServicio.eliminarItem(idItem);
        return ResponseEntity.ok("Item eliminado correctamente");
    }


    //actualizar el carrito
    @PutMapping("/actualizar/{idItem}")
    public ResponseEntity<?> actualizar(@PathVariable Long idItem, @RequestParam int cantidad) {
        carritoServicio.actualizarCantidad(idItem, cantidad);
        return ResponseEntity.ok("Cantidad actualizada correctamente");
    }

    
    //vaciar carrito
    @PutMapping("/vaciar/{idCarrito}")
    public ResponseEntity<?> vaciar(@PathVariable Long idCarrito) {
        carritoServicio.vaciarCarrito(idCarrito);
        return ResponseEntity.ok("Carrito vaciado correctamente");
    }

}
