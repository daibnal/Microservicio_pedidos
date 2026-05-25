package duoc.dairys.pedidos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import duoc.dairys.pedidos.model.Carrito;

public interface CarritoRepositorio extends JpaRepository<Carrito, Long> {
}

