package duoc.dairys.pedidos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import duoc.dairys.pedidos.model.ItemCarrito;
import duoc.dairys.pedidos.repository.ItemCarritoRepositorio;


@Service
public class ItemServicio {

    @Autowired
    private ItemCarritoRepositorio itemCarritoRepositorio;

    //crear item
    public ItemCarrito crear(ItemCarrito item) {
        if (item.getCantidad() == null || item.getCantidad() <= 0) {
            throw new IllegalArgumentException("Cantidad inválida");
        }
        if (item.getPrecioUnitario() == null || item.getPrecioUnitario() <= 0) {
            throw new IllegalArgumentException("Precio inválido");
        }

        item.calcularSubtotal();

        return itemCarritoRepositorio.save(item);
    }

    //listar items
    public List<ItemCarrito> listar() {
        return itemCarritoRepositorio.findAll();
    }

    //eliminar
    public void eliminar(Long id) {
        itemCarritoRepositorio.deleteById(id);
    }
    
}
