package duoc.dairys.pedidos.DTO;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor

public class PedidoDTO {
    @NotNull
    private Long idCliente;
    private String direccion;

    private List<DetallPedidoDTO> detalles;


}
