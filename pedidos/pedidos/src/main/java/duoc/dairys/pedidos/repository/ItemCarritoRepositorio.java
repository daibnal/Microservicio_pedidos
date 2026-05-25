package duoc.dairys.pedidos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import duoc.dairys.pedidos.model.ItemCarrito;

public interface ItemCarritoRepositorio extends JpaRepository<ItemCarrito, Long> {

}
