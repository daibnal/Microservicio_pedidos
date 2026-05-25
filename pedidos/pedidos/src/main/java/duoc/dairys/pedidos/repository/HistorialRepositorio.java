package duoc.dairys.pedidos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import duoc.dairys.pedidos.model.HistorialPedido;

public interface HistorialRepositorio extends JpaRepository<HistorialPedido, Long> {
    
}
