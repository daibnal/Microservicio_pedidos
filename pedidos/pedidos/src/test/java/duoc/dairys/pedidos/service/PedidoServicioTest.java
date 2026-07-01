package duoc.dairys.pedidos.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import duoc.dairys.pedidos.DTO.DetallPedidoDTO;
import duoc.dairys.pedidos.DTO.PedidoDTO;
import duoc.dairys.pedidos.DTO.VentaDTO;
import duoc.dairys.pedidos.client.ConVenta;
import duoc.dairys.pedidos.client.VerCliente;
import duoc.dairys.pedidos.model.Pedido;
import duoc.dairys.pedidos.repository.PedidoRepositorio;

public class PedidoServicioTest {
     @Mock
    private PedidoRepositorio pedidoRepositorio;

    @Mock
    private RestTemplate restTemplate; // Se mockea aunque no se llame directamente para evitar errores de inyección

    @Mock
    private VerCliente cliente;

    @Mock
    private ConVenta venta;

    @InjectMocks
    private PedidoServicio pedidoServicio;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void crearPedido_Exito() {
        // 1. Preparar datos
        DetallPedidoDTO detDTO = new DetallPedidoDTO();
        detDTO.setIdProducto(1L);
        detDTO.setNombreProducto("Leche");
        detDTO.setCantidad(2);
        detDTO.setPrecioUnitario(1500.0); // 2 * 1500 = 3000

        PedidoDTO dto = new PedidoDTO();
        dto.setIdCliente(10L);
        dto.setDireccion("Calle Falsa 123");
        dto.setDetalles(List.of(detDTO));

        Pedido pedidoGuardadoMock = new Pedido();
        pedidoGuardadoMock.setIdPedido(50L);
        pedidoGuardadoMock.setIdCliente(10L);

        // 2. Comportamiento de los Mocks
        when(cliente.existeCliente(10L)).thenReturn(true);
        when(pedidoRepositorio.save(any(Pedido.class))).thenReturn(pedidoGuardadoMock);

        // 3. Ejecutar
        Pedido resultado = pedidoServicio.crearPedido(dto);

        // 4. Verificaciones
        assertNotNull(resultado);
        assertEquals(50L, resultado.getIdPedido());
        
        // Verificamos que se haya intentado registrar la venta en el otro microservicio
        verify(venta, times(1)).registrarVenta(any(VentaDTO.class));
        verify(pedidoRepositorio, times(1)).save(any(Pedido.class));
    }

    @Test
    void crearPedido_ClienteInvalido_LanzaExcepcion() {
        PedidoDTO dto = new PedidoDTO();
        dto.setIdCliente(99L);

        when(cliente.existeCliente(99L)).thenReturn(false);

        // Verificamos que el servicio estalle con RuntimeException
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            pedidoServicio.crearPedido(dto);
        });

        assertEquals("El cliente no existe o está inactivo", exception.getMessage());
        
        // Verificamos que NUNCA se haya guardado nada
        verify(pedidoRepositorio, times(0)).save(any());
        verify(venta, times(0)).registrarVenta(any());
    }

    @Test
    void obtenerPedido_Existe() {
        Pedido p = new Pedido();
        p.setIdPedido(1L);

        when(pedidoRepositorio.findById(1L)).thenReturn(Optional.of(p));

        Pedido resultado = pedidoServicio.obtenerPedido(1L);
        assertNotNull(resultado);
        assertEquals(1L, resultado.getIdPedido());
    }

    @Test
    void obtenerPedido_NoExiste() {
        when(pedidoRepositorio.findById(99L)).thenReturn(Optional.empty());

        Pedido resultado = pedidoServicio.obtenerPedido(99L);
        assertNull(resultado);
    }

    @Test
    void listarPedidos_Lleno() {
        Pedido p1 = new Pedido();
        Pedido p2 = new Pedido();
        when(pedidoRepositorio.findAll()).thenReturn(List.of(p1, p2));

        List<Pedido> resultado = pedidoServicio.listarPedidos();
        assertEquals(2, resultado.size());
    }

    @Test
    void cambiarEstado_Existe() {
        Pedido p = new Pedido();
        p.setIdPedido(1L);
        p.setEstadoPedido("PENDIENTE");

        when(pedidoRepositorio.findById(1L)).thenReturn(Optional.of(p));
        when(pedidoRepositorio.save(any(Pedido.class))).thenReturn(p);

        Pedido resultado = pedidoServicio.cambiarEstado(1L, "ENTREGADO");

        assertNotNull(resultado);
        assertEquals("ENTREGADO", p.getEstadoPedido()); // El objeto fue modificado
        verify(pedidoRepositorio, times(1)).save(p);
    }

    @Test
    void cambiarEstado_NoExiste() {
        when(pedidoRepositorio.findById(99L)).thenReturn(Optional.empty());

        Pedido resultado = pedidoServicio.cambiarEstado(99L, "ENTREGADO");

        assertNull(resultado);
        verify(pedidoRepositorio, times(0)).save(any());
    }
}
