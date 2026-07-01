package duoc.dairys.pedidos.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import duoc.dairys.pedidos.model.HistorialPedido;
import duoc.dairys.pedidos.model.Pedido;
import duoc.dairys.pedidos.repository.HistorialRepositorio;
import duoc.dairys.pedidos.repository.PedidoRepositorio;

public class HistorialServicioTest {
    @Mock
    private HistorialRepositorio historialRepositorio;

    @Mock
    private PedidoRepositorio pedidoRepositorio;

    @InjectMocks
    private HistorialServicio historialServicio;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void crear_Exito() {
        HistorialPedido historial = new HistorialPedido();
        historial.setIdCliente(10L);

        when(historialRepositorio.save(any(HistorialPedido.class))).thenReturn(historial);

        HistorialPedido resultado = historialServicio.crear(historial);

        assertNotNull(resultado);
        assertEquals(10L, resultado.getIdCliente());
        verify(historialRepositorio, times(1)).save(historial);
    }

    @Test
    void obtener_Existe() {
        HistorialPedido historial = new HistorialPedido();
        historial.setIdHistorial(1L);

        when(historialRepositorio.findById(1L)).thenReturn(Optional.of(historial));

        HistorialPedido resultado = historialServicio.obtener(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getIdHistorial());
    }

    @Test
    void obtener_NoExiste() {
        when(historialRepositorio.findById(99L)).thenReturn(Optional.empty());

        HistorialPedido resultado = historialServicio.obtener(99L);

        assertNull(resultado); // Porque el servicio usa .orElse(null)
    }

    @Test
    void listar_ConDatos() {
        HistorialPedido h1 = new HistorialPedido();
        HistorialPedido h2 = new HistorialPedido();
        when(historialRepositorio.findAll()).thenReturn(List.of(h1, h2));

        List<HistorialPedido> resultado = historialServicio.listar();

        assertEquals(2, resultado.size());
        verify(historialRepositorio, times(1)).findAll();
    }

    @Test
    void agregarPedido_HistorialNoExiste() {
        Pedido pedido = new Pedido();
        when(historialRepositorio.findById(99L)).thenReturn(Optional.empty());

        HistorialPedido resultado = historialServicio.agregarPedido(99L, pedido);

        assertNull(resultado);
        verify(historialRepositorio, times(0)).save(any());
    }

    @Test
    void agregarPedido_Exito() {
        HistorialPedido historial = new HistorialPedido();
        historial.setIdHistorial(1L);
        historial.setPedidos(new ArrayList<>()); // Inicializamos la lista

        Pedido pedido = new Pedido();
        pedido.setIdPedido(100L);

        when(historialRepositorio.findById(1L)).thenReturn(Optional.of(historial));
        when(historialRepositorio.save(any(HistorialPedido.class))).thenReturn(historial);

        HistorialPedido resultado = historialServicio.agregarPedido(1L, pedido);

        assertNotNull(resultado);
        assertEquals(1, resultado.getPedidos().size());
        verify(historialRepositorio, times(1)).save(historial);
    }

}
