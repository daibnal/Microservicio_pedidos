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


    //crear detalle y calcular total
    public DetallePedido crearDetalle(DetallePedido detalle) {
        if (detalle.getCantidad() == null || detalle.getCantidad() <= 0) {
            throw new IllegalArgumentException("Cantidad inválida");
        }

        if (detalle.getPrecioUnitario() == null || detalle.getPrecioUnitario() <= 0) {
            throw new IllegalArgumentException("Precio inválido");
        }

        if (detalle.getPedido() == null || detalle.getPedido().getIdPedido() == null) {
            throw new IllegalArgumentException("Pedido inválido");
        }

        detalle.calcularSubtotal();
        return detallePedidoRepositorio.save(detalle);
    }

    //listar detalle del pedido
    public List<DetallePedido> obtenerDetalles() {
        return detallePedidoRepositorio.findAll();
    }
}
