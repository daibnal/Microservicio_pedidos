package duoc.dairys.pedidos.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "detalle_pedido")
public class DetallePedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle")
    private Long idDetalle;

    @NotNull(message = "El producto es obligatorio")
    @Column(name = "id_producto", nullable = false)
    private Long idProducto;

    @NotBlank(message = "El nombre del producto es obligatorio")
    @Column(name = "nombre_producto", nullable = false)
    private String nombreProducto;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser mayor a 0")
    @Column(name = "cantidad", nullable = false)
    private Long cantidad;


    @NotNull(message = "El precio unitario es obligatorio")
    @Min(value = 0, message = "El precio no puede ser negativo")
    @Column(name = "precio_unitario", nullable = false)
    private Double precioUnitario;

    @Min(0)
    @NotNull(message = "El subtotal es obligatorio")
    @Column(name = "subtotal", nullable = false)
    private Double subtotal;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    public void calcularSubtotal() {
        if (this.cantidad == null || this.precioUnitario == null) {
            this.subtotal = 0.0;
            return;
        }
        this.subtotal = this.cantidad * this.precioUnitario;
    }
}
