package duoc.dairys.pedidos.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import duoc.dairys.pedidos.model.DetallePedido;

public interface DetallePedidoRepositorio extends JpaRepository<DetallePedido, Long> {
    
}
