package duoc.dairys.pedidos.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import duoc.dairys.pedidos.DTO.PedidoDTO;
import duoc.dairys.pedidos.model.Pedido;
import duoc.dairys.pedidos.service.PedidoServicio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(PedidoController.class)
@ActiveProfiles("test")
public class PedidoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PedidoServicio pedidoServicio;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void crear_Exito() throws Exception {
        PedidoDTO dtoEntrada = new PedidoDTO();
        dtoEntrada.setIdCliente(1L);

        Pedido pedidoRetorno = new Pedido();
        pedidoRetorno.setIdPedido(100L);
        pedidoRetorno.setEstadoPedido("PENDIENTE");

        Mockito.when(pedidoServicio.crearPedido(any(PedidoDTO.class))).thenReturn(pedidoRetorno);

        mockMvc.perform(post("/api/ecomarket/v1/pedido/crear")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoEntrada)))
                .andExpect(status().isCreated()) // 201
                .andExpect(jsonPath("$.mensaje", is("Pedido creado correctamente")))
                .andExpect(jsonPath("$.data.idPedido", is(100)));
    }

    @Test
    void obtener_Exito() throws Exception {
        Pedido p = new Pedido();
        p.setIdPedido(5L);
        p.setDireccion("Avenida 123");

        Mockito.when(pedidoServicio.obtenerPedido(5L)).thenReturn(p);

        mockMvc.perform(get("/api/ecomarket/v1/pedido/5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensaje", is("Pedido encontrado")))
                .andExpect(jsonPath("$.data.direccion", is("Avenida 123")));
    }

    @Test
    void listar_Exito() throws Exception {
        Pedido p1 = new Pedido();
        p1.setIdPedido(1L);
        Pedido p2 = new Pedido();
        p2.setIdPedido(2L);

        List<Pedido> lista = Arrays.asList(p1, p2);

        Mockito.when(pedidoServicio.listarPedidos()).thenReturn(lista);

        mockMvc.perform(get("/api/ecomarket/v1/pedido"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensaje", is("Listado de pedidos")))
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.data[0].idPedido", is(1)));
    }

    @Test
    void cambiarEstado_Exito() throws Exception {
        Pedido pActualizado = new Pedido();
        pActualizado.setIdPedido(1L);
        pActualizado.setEstadoPedido("EN_REPARTO");

        Mockito.when(pedidoServicio.cambiarEstado(eq(1L), eq("EN_REPARTO"))).thenReturn(pActualizado);

        mockMvc.perform(put("/api/ecomarket/v1/pedido/estado/1")
                        .param("estado", "EN_REPARTO"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensaje", is("Estado actualizado")))
                .andExpect(jsonPath("$.data.estadoPedido", is("EN_REPARTO")));
    }
}
