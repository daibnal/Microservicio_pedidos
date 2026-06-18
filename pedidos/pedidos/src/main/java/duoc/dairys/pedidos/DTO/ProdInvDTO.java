package duoc.dairys.pedidos.DTO;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdInvDTO {

    private Long idProducto;
    private String nombProducto;
    private String codigo;
    private LocalDate caducidad;
    private int stockActual;
    private int stockMin;
    private String estadoprod;
    
}
