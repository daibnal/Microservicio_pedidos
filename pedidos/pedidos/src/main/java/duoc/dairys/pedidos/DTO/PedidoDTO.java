package duoc.dairys.pedidos.DTO;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDTO {
    @NotNull
    private Long idCliente;

    @NotNull
    private LocalDateTime fecha;

    @NotBlank
    private String estado;

    @NotNull
    private Double total;
}
