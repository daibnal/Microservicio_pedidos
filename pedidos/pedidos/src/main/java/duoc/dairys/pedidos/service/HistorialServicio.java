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
    private PedidoRepositorio pedidoRepositorio;

    //crear historial
    public HistorialPedido crear(HistorialPedido historial) {
        return historialRepositorio.save(historial);
    }

    // obtener historial por id
    public HistorialPedido obtener(Long id) {
        return historialRepositorio.findById(id).orElse(null);
    }
    // listar historiales
    public List<HistorialPedido> listar() {
        return historialRepositorio.findAll();
    }

    // agregar pedido al historial
    public HistorialPedido agregarPedido(Long idHistorial, Pedido pedido) {
        HistorialPedido historial = historialRepositorio.findById(idHistorial).orElse(null);
        if (historial == null) return null;

        historial.getPedidos().add(pedido);
        return historialRepositorio.save(historial);
    }



    
}
