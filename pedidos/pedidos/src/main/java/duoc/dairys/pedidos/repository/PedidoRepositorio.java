package duoc.dairys.pedidos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import duoc.dairys.pedidos.model.Pedido;

public interface PedidoRepositorio extends JpaRepository<Pedido, Long> {
    
}
