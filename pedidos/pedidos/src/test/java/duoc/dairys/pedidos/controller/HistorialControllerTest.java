package duoc.dairys.pedidos.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import duoc.dairys.pedidos.model.HistorialPedido;
import duoc.dairys.pedidos.service.HistorialServicio;


@WebMvcTest(HistorialControlador.class)
@ActiveProfiles("test")
public class HistorialControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private HistorialServicio historialServicio;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void crear_Exito() throws Exception {
        HistorialPedido nuevo = new HistorialPedido();
        nuevo.setIdCliente(5L);

        HistorialPedido creado = new HistorialPedido();
        creado.setIdHistorial(1L);
        creado.setIdCliente(5L);

        Mockito.when(historialServicio.crear(any(HistorialPedido.class))).thenReturn(creado);

        mockMvc.perform(post("/historialPedidos/crear")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nuevo)))
                .andExpect(status().isCreated()) // 201
                .andExpect(jsonPath("$.mensaje", is("Historial creado correctamente")))
                .andExpect(jsonPath("$.data.idHistorial", is(1)))
                .andExpect(jsonPath("$.data.idCliente", is(5)));
    }

    @Test
    void listar_Exito() throws Exception {
        HistorialPedido h1 = new HistorialPedido();
        h1.setIdHistorial(1L);
        HistorialPedido h2 = new HistorialPedido();
        h2.setIdHistorial(2L);

        List<HistorialPedido> lista = Arrays.asList(h1, h2);

        Mockito.when(historialServicio.listar()).thenReturn(lista);

        mockMvc.perform(get("/historialPedidos"))
                .andExpect(status().isOk()) // 200
                .andExpect(jsonPath("$.mensaje", is("Lista de historiales obtenida correctamente")))
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.data[0].idHistorial", is(1)));
    }

    @Test
    void obtener_Exito() throws Exception {
        HistorialPedido h = new HistorialPedido();
        h.setIdHistorial(10L);
        h.setIdCliente(20L);

        Mockito.when(historialServicio.obtener(10L)).thenReturn(h);

        mockMvc.perform(get("/historialPedidos/10"))
                .andExpect(status().isOk()) // 200
                .andExpect(jsonPath("$.mensaje", is("Historial encontrado")))
                .andExpect(jsonPath("$.data.idHistorial", is(10)))
                .andExpect(jsonPath("$.data.idCliente", is(20)));
    }
}
