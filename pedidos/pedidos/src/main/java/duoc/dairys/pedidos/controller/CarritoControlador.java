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
import duoc.dairys.pedidos.DTO.ResponseDTO;
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
    public ResponseEntity<ResponseDTO> agregarItem(@PathVariable Long idCarrito, @RequestBody ItemCarritoDTO dto) {
        Carrito carrito = carritoServicio.agregarItem(idCarrito, dto);

        return ResponseEntity.ok(new ResponseDTO("Item agregado correctamente", carrito));
    }



    //eliminar item al carrito
    @DeleteMapping("/item/{idItem}")
    public ResponseEntity<ResponseDTO> eliminar(@PathVariable Long idItem) {
        Carrito carrito = carritoServicio.eliminarItem(idItem);

        return ResponseEntity.ok(new ResponseDTO("Item eliminado correctamente", carrito));
    }


    //actualizar el carrito
    @PutMapping("/item/{idItem}")
    public ResponseEntity<ResponseDTO> actualizar(@PathVariable Long idItem, @RequestParam int cantidad) {
        Carrito carrito = carritoServicio.actualizarCantidad(idItem, cantidad);

        return ResponseEntity.ok(new ResponseDTO("Cantidad actualizada correctamente", carrito));
    }
    

    //vaciar carrito
    @PutMapping("/vaciar/{idCarrito}")
    public ResponseEntity<ResponseDTO> vaciar(@PathVariable Long idCarrito) {
        Carrito carrito = carritoServicio.vaciarCarrito(idCarrito);

        return ResponseEntity.ok(new ResponseDTO("Carrito vaciado correctamente", carrito));
    }

}
