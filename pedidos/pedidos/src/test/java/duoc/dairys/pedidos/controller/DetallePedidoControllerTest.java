package duoc.dairys.pedidos.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import duoc.dairys.pedidos.model.DetallePedido;
import duoc.dairys.pedidos.service.DetallePedidoServicio;

@WebMvcTest(DetallePedidoControlador.class)
@ActiveProfiles("test")
public class DetallePedidoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DetallePedidoServicio detallePedidoServicio;

    @Test
    void listarDetalles_Exito() throws Exception {
        DetallePedido d1 = new DetallePedido();
        d1.setNombreProducto("Queso");
        DetallePedido d2 = new DetallePedido();
        d2.setNombreProducto("Yogurt");

        List<DetallePedido> lista = Arrays.asList(d1, d2);

        Mockito.when(detallePedidoServicio.listar()).thenReturn(lista);

        mockMvc.perform(get("/detalle"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensaje", is("Lista de detalles obtenida correctamente")))
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.data[0].nombreProducto", is("Queso")));
    }

    @Test
    void obtenerDetalle_Exito() throws Exception {
        DetallePedido d = new DetallePedido();
        d.setIdDetalle(1L);
        d.setNombreProducto("Mantequilla");

        Mockito.when(detallePedidoServicio.obtener(1L)).thenReturn(d);

        mockMvc.perform(get("/detalle/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensaje", is("Detalle encontrado")))
                .andExpect(jsonPath("$.data.idDetalle", is(1)))
                .andExpect(jsonPath("$.data.nombreProducto", is("Mantequilla")));
    }

    @Test
    void eliminarDetalle_Exito() throws Exception {
        Mockito.doNothing().when(detallePedidoServicio).eliminar(1L);

        mockMvc.perform(delete("/detalle/eliminar/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensaje", is("Detalle eliminado correctamente")))
                .andExpect(jsonPath("$.data", is(nullValue())));
    }
}
