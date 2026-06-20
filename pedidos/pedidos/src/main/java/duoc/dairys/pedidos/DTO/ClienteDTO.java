package duoc.dairys.pedidos.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {
    private Long id;
    private String rut;
    private String pNombre;
    private String pApellido;
    private String correo;
    private String estado;

}
