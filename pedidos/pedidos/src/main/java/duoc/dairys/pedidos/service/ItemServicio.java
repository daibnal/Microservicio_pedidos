package duoc.dairys.pedidos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import duoc.dairys.pedidos.DTO.ItemCarritoDTO;
import duoc.dairys.pedidos.model.ItemCarrito;
import duoc.dairys.pedidos.repository.ItemCarritoRepositorio;


@Service
public class ItemServicio {

    @Autowired
    private ItemCarritoRepositorio itemCarritoRepositorio;

    //crear item
    public ItemCarrito crear(ItemCarritoDTO dto) {
        if (dto.getCantidad() == null || dto.getCantidad() <= 0) {
            throw new IllegalArgumentException("Cantidad inválida");
        }

        if (dto.getPrecioUnitario() == null || dto.getPrecioUnitario() <= 0) {
            throw new IllegalArgumentException("Precio inválido");
        }

        ItemCarrito item = new ItemCarrito();

        item.setIdProducto(dto.getIdProducto());
        item.setNombreProducto(dto.getNombreProducto());
        item.setCantidad(dto.getCantidad());
        item.setPrecioUnitario(dto.getPrecioUnitario());

        item.calcularSubtotal();

        return itemCarritoRepositorio.save(item);
    }

    //listar items
    public List<ItemCarrito> listar() {
        return itemCarritoRepositorio.findAll();
    }

    //eliminar
    public void eliminar(Long id) {
        if (!itemCarritoRepositorio.existsById(id)) {
            throw new IllegalArgumentException("Item no encontrado");
        }
        itemCarritoRepositorio.deleteById(id);
    }
    
}
