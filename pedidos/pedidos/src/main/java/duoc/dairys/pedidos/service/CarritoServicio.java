package duoc.dairys.pedidos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import duoc.dairys.pedidos.DTO.ItemCarritoDTO;
import duoc.dairys.pedidos.model.Carrito;
import duoc.dairys.pedidos.model.ItemCarrito;
import duoc.dairys.pedidos.repository.CarritoRepositorio;
import duoc.dairys.pedidos.repository.ItemCarritoRepositorio;

@Service
public class CarritoServicio {

    @Autowired
    private CarritoRepositorio carritoRepositorio;

    @Autowired
    private ItemCarritoRepositorio itemCarritoRepositorio;

    //ver carrito por id
    public Carrito obtenerCarrito(Long idCarrito) {
        return carritoRepositorio.findById(idCarrito).orElse(null);
    }

    //ver todos los carritos
    public List<Carrito> listarCarritos() {
        return carritoRepositorio.findAll();
    }

    //crear carrito
    public Carrito crearCarrito(Carrito carrito) {
        carrito.setTotal(0.0);
        return carritoRepositorio.save(carrito);
    }

    //agregar item al carrito
    public Carrito agregarItem(Long idCarrito, ItemCarritoDTO dto) {
        Carrito carrito = carritoRepositorio.findById(idCarrito).orElse(null);
        if (carrito == null) return null;

        ItemCarrito item = new ItemCarrito();

        item.setIdProducto(dto.getIdProducto());
        item.setNombreProducto(dto.getNombreProducto());
        item.setCantidad(dto.getCantidad());
        item.setPrecioUnitario(dto.getPrecioUnitario());

        item.calcularSubtotal();
        item.setCarrito(carrito);

        itemCarritoRepositorio.save(item);

        carrito.getItems().add(item);
        carritoRepositorio.save(carrito);

        calcularTotal(idCarrito);
        return carrito;
    }

    //eliminar item del carrito
    public Carrito eliminarItem(Long idItem) {
        ItemCarrito item = itemCarritoRepositorio.findById(idItem).orElse(null);
        if (item == null) return null;

        Carrito carrito = item.getCarrito();

        itemCarritoRepositorio.deleteById(idItem);

        calcularTotal(carrito.getIdCarrito());
        return carrito;
    }

    //actualizar cantidad 
    public Carrito actualizarCantidad(Long idItem, int cantidad) {
        ItemCarrito item = itemCarritoRepositorio.findById(idItem).orElse(null);
        if (item == null) return null;

        item.setCantidad(cantidad);
        item.calcularSubtotal();

        itemCarritoRepositorio.save(item);

        calcularTotal(item.getCarrito().getIdCarrito());

        return item.getCarrito();
    }


    //calcular total del carrito
    public void calcularTotal(Long idCarrito) {
        Carrito carrito = carritoRepositorio.findById(idCarrito).orElse(null);
        if (carrito == null) return;

        double total = carrito.getItems().stream().mapToDouble(ItemCarrito::getSubtotal).sum();

        carrito.setTotal(total);
        carritoRepositorio.save(carrito);
    }

    //vaciar carrito
    public Carrito vaciarCarrito(Long idCarrito) {
        Carrito carrito = carritoRepositorio.findById(idCarrito).orElse(null);
        if (carrito == null) return null;

        // eliminar en base de dato
        itemCarritoRepositorio.deleteAll(carrito.getItems());

        carrito.getItems().clear();
        carrito.setTotal(0.0);

        return carritoRepositorio.save(carrito);
    }

}
