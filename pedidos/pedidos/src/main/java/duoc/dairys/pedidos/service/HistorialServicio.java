package duoc.dairys.pedidos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import duoc.dairys.pedidos.model.HistorialPedido;
import duoc.dairys.pedidos.model.Pedido;
import duoc.dairys.pedidos.repository.HistorialRepositorio;
import duoc.dairys.pedidos.repository.PedidoRepositorio;

@Service
public class HistorialServicio {

    @Autowired
    private HistorialRepositorio historialRepositorio;

    @Autowired
    private PedidoRepositorio pedidoRepo;

    //crear historial
    public HistorialPedido crearHistorial(HistorialPedido historial) {
        return historialRepositorio.save(historial);
    }

    //agregar pedido al historial
    public HistorialPedido agregarPedido(Long idHistorial, Long idPedido) {

        HistorialPedido historial = historialRepositorio.findById(idHistorial).orElse(null);
        if (historial == null) return null;

        Pedido pedido = pedidoRepo.findById(idPedido).orElse(null);
        if (pedido == null) return null;

        historial.getPedidos().add(pedido);
        return historialRepositorio.save(historial);
    }

    //obtener pedidos del historial
    public List<Pedido> obtenerPedidos(Long idHistorial) {
        HistorialPedido historial = historialRepositorio.findById(idHistorial).orElse(null);
        if (historial == null) return null;

        return historial.getPedidos();
    }
    
}
