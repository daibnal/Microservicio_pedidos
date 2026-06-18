package duoc.dairys.pedidos.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import duoc.dairys.pedidos.DTO.DetallPedidoDTO;
import duoc.dairys.pedidos.DTO.PedidoDTO;
import duoc.dairys.pedidos.model.DetallePedido;
import duoc.dairys.pedidos.model.Pedido;
import duoc.dairys.pedidos.repository.HistorialRepositorio;
import duoc.dairys.pedidos.repository.PedidoRepositorio;

@Service
public class PedidoServicio {
    
     @Autowired
    private PedidoRepositorio pedidoRepositorio;

    @Autowired
    private HistorialRepositorio historialRepositorio;

    //crear pedido
    public Pedido crearPedido(PedidoDTO dto) {

        Pedido pedido = new Pedido();

        pedido.setIdCliente(dto.getIdCliente());
        pedido.setDireccion(dto.getDireccion());
        pedido.setEstadoPedido("PENDIENTE");
        pedido.setFechaPedido(LocalDateTime.now());

        List<DetallePedido> detalles = new ArrayList<>();

        for (DetallPedidoDTO d : dto.getDetalles()) {

            DetallePedido detalle = new DetallePedido();

            detalle.setIdProducto(d.getIdProducto());
            detalle.setNombreProducto(d.getNombreProducto());
            detalle.setCantidad(d.getCantidad());
            detalle.setPrecioUnitario(d.getPrecioUnitario());

            detalle.calcularSubtotal();
            detalle.setPedido(pedido);

            detalles.add(detalle);
        }
        pedido.setDetalles(detalles);
        pedido.calcularTotal();

        return pedidoRepositorio.save(pedido);
    }


    //obtener pedido por id
    public Pedido obtenerPedido(Long id) {
        return pedidoRepositorio.findById(id).orElse(null);
    }

    //listar pedidos
    public List<Pedido> listarPedidos() {
        return pedidoRepositorio.findAll();
    }

    //cambiar el estado del pedido
    public Pedido cambiarEstado(Long id, String estado) {

        Pedido pedido = pedidoRepositorio.findById(id).orElse(null);

        if (pedido == null) {
            return null;
        }
        pedido.setEstadoPedido(estado);

        return pedidoRepositorio.save(pedido);
    }

    //cancelar pedido
    public boolean cancelarPedido(Long id) {
    Pedido pedido = pedidoRepositorio.findById(id).orElse(null);

    if (pedido == null) {
        return false;
    }   
    pedido.setEstadoPedido("CANCELADO");
    pedidoRepositorio.save(pedido);
    return true;
    }

}