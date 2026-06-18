package duoc.dairys.pedidos.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pedidos")

public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPedido;

    @Column(name = "cliente_id")
    private Long idCliente;

    @NotNull(message = "La fecha es obligatoria")
    @Column(name = "fecha_pedido")
    private LocalDateTime fechaPedido;

    @NotBlank(message = "El estado no puede estar vacio")
    @Column(name = "estado_pedido")
    private String estadoPedido;

    @NotBlank(message = "la direccion no puede estar vacia")
    @Column(name = "direccion")
    private String direccion;

    @Min(0)
    @NotNull(message = "El monto es obligatorio")
    @Column(name = "monto_total")
    private Double total;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<DetallePedido> detalles = new ArrayList<>();
    
    public void calcularTotal() {
        this.total = detalles.stream().mapToDouble(DetallePedido::getSubtotal).sum();
    }
}
