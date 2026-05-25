package duoc.dairys.pedidos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import duoc.dairys.pedidos.DTO.PedidoDTO;
import duoc.dairys.pedidos.model.Pedido;
import duoc.dairys.pedidos.repository.PedidoRepositorio;

@Service
public class PedidoServicio {
    
    @Autowired
    private PedidoRepositorio pedidoRepositorio;

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

    //calcular subtotal + iva
    public Double calcularTotal(Double subtotal, Double iva) {
        return subtotal + iva;
    }





}
