package duoc.dairys.pedidos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import duoc.dairys.pedidos.model.DetallePedido;
import duoc.dairys.pedidos.repository.DetallePedidoRepositorio;

@Service
public class DetallePedidoServicio {
    
    @Autowired
    private DetallePedidoRepositorio detallePedidoRepositorio;


    //obtener detalle
    public DetallePedido obtener(Long id) {
        return detallePedidoRepositorio.findById(id).orElse(null);
    }

    //listar detalles
    public List<DetallePedido> listar() {
        return detallePedidoRepositorio.findAll();
    }

    //eliminar detalle
    public void eliminar(Long id) {
        detallePedidoRepositorio.deleteById(id);
    }
}
