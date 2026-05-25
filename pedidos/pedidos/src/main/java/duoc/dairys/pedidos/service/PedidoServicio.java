package duoc.dairys.pedidos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import duoc.dairys.pedidos.DTO.PedidoDTO;
import duoc.dairys.pedidos.model.DetallePedido;
import duoc.dairys.pedidos.model.Pedido;
import duoc.dairys.pedidos.repository.DetallePedidoRepositorio;
import duoc.dairys.pedidos.repository.PedidoRepositorio;

@Service
public class PedidoServicio {
    
    @Autowired
    private PedidoRepositorio pedidoRepositorio;

    @Autowired
    private DetallePedidoRepositorio detallePedidoRepositorio;

    //crear pedido
    public Pedido crearPedido(PedidoDTO dto){
        Pedido pedido = new Pedido();
        pedido.setIdCliente(dto.getIdCliente());
        pedido.setFecha(dto.getFecha());
        pedido.setEstado("PENDIENTE");
        pedido.setTotal(dto.getTotal());

        return pedidoRepositorio.save(pedido);
    }

    //obtener pedidos
    public List<Pedido> obtenerPedidos() {
        return pedidoRepositorio.findAll();
    }

    //cancelar pedido
    public boolean cancelarPedido(Long id) {
    Pedido pedido = pedidoRepositorio.findById(id).orElse(null);

    if (pedido == null) {
        return false;
    }   
    pedido.setEstado("CANCELADO");
    pedidoRepositorio.save(pedido);
    return true;
    }

    //cambiar estado del pedido
    public boolean cambiarEstado(Long id, String estado) {
    Pedido pedido = pedidoRepositorio.findById(id).orElse(null);

    if (pedido == null) {
        return false;
    }

    pedido.setEstado(estado);
    pedidoRepositorio.save(pedido);
    return true;
    }

    //calcular total
    public Double calcularTotal(Long idPedido) {
        List<DetallePedido> todos = detallePedidoRepositorio.findAll();

        double total = 0;

        for (DetallePedido d : todos) {
            if (d.getPedido() != null &&
                d.getPedido().getIdPedido().equals(idPedido)) {

                total += d.getSubtotal();
            }
        }
        return total;
        }

    //actualizar total
    public boolean actualizarTotal(Long idPedido) {
        Pedido pedido = pedidoRepositorio.findById(idPedido).orElse(null);

        if (pedido == null) {
            return false;
        }

        double total = calcularTotal(idPedido);

        pedido.setTotal(total);

        pedidoRepositorio.save(pedido);
        return true;
    }


}
