package duoc.dairys.pedidos.controller;

import java.util.HashMap;
import java.util.Map;

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
    @GetMapping("/{idCarrito}")
    public ResponseEntity<?> obtenerCarrito(@PathVariable Long idCarrito) {
        return ResponseEntity.ok(carritoServicio.obtenerCarrito(idCarrito));
    }

    //listar carritos
    @GetMapping
    public ResponseEntity<?> listar() {
        return ResponseEntity.ok(carritoServicio.listarCarritos());
    }

    //crear carrito
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Carrito carrito) {
        return ResponseEntity.status(201).body(carritoServicio.crearCarrito(carrito));
    }


    //agregar item al carrito
    @PostMapping("/{idCarrito}/item")
    public ResponseEntity<?> agregarItem(@PathVariable Long idCarrito, @RequestBody ItemCarritoDTO dto) {
        Carrito carrito = carritoServicio.agregarItem(idCarrito, dto);
        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "Item agregado correctamente");
        response.put("data", carrito);
        return ResponseEntity.ok(response);
    }



    //eliminar item al carrito
    @DeleteMapping("/item/{idItem}")
    public ResponseEntity<?> eliminar(@PathVariable Long idItem) {
        Carrito carrito = carritoServicio.eliminarItem(idItem);

        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "Item eliminado correctamente");
        response.put("data", carrito);

        return ResponseEntity.ok(response);
    }

    //actualizar el carrito
    @PutMapping("/item/{idItem}")
public ResponseEntity<?> actualizar(@PathVariable Long idItem, @RequestParam int cantidad) {
    Carrito carrito = carritoServicio.actualizarCantidad(idItem, cantidad);
    Map<String, Object> response = new HashMap<>();
    response.put("mensaje", "Cantidad actualizada correctamente");
    response.put("data", carrito);

    return ResponseEntity.ok(response);
    }


    //vaciar carrito
    @PutMapping("/vaciar/{idCarrito}")
    public ResponseEntity<?> vaciar(@PathVariable Long idCarrito) {
        Carrito carrito = carritoServicio.vaciarCarrito(idCarrito);

        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "Carrito vaciado correctamente");
        response.put("data", carrito);

        return ResponseEntity.ok(response);
    }

}
