package duoc.dairys.pedidos.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

import duoc.dairys.pedidos.model.DetallePedido;
import duoc.dairys.pedidos.repository.DetallePedidoRepositorio;

public class DetallePedidoServicioTest {
    @Mock
    private DetallePedidoRepositorio detallePedidoRepositorio;

    @InjectMocks
    private DetallePedidoServicio detallePedidoServicio;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void obtener_Existe() {
        DetallePedido detalle = new DetallePedido();
        detalle.setIdDetalle(1L);
        detalle.setNombreProducto("Leche");

        when(detallePedidoRepositorio.findById(1L)).thenReturn(Optional.of(detalle));

        DetallePedido resultado = detallePedidoServicio.obtener(1L);

        assertEquals(1L, resultado.getIdDetalle());
        assertEquals("Leche", resultado.getNombreProducto());
    }

    @Test
    void obtener_NoExiste() {
        when(detallePedidoRepositorio.findById(99L)).thenReturn(Optional.empty());

        DetallePedido resultado = detallePedidoServicio.obtener(99L);

        assertNull(resultado);
    }

    @Test
    void listar_ConDatos() {
        DetallePedido d1 = new DetallePedido();
        DetallePedido d2 = new DetallePedido();
        when(detallePedidoRepositorio.findAll()).thenReturn(List.of(d1, d2));

        List<DetallePedido> resultado = detallePedidoServicio.listar();

        assertEquals(2, resultado.size());
        verify(detallePedidoRepositorio, times(1)).findAll();
    }

    @Test
    void listar_Vacio() {
        when(detallePedidoRepositorio.findAll()).thenReturn(new ArrayList<>());

        List<DetallePedido> resultado = detallePedidoServicio.listar();

        assertTrue(resultado.isEmpty());
        verify(detallePedidoRepositorio, times(1)).findAll();
    }

    @Test
    void eliminar_Exito() {
        detallePedidoServicio.eliminar(1L);

        verify(detallePedidoRepositorio, times(1)).deleteById(1L);
    }
}
