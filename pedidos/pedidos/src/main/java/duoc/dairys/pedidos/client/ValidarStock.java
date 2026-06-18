package duoc.dairys.pedidos.client;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import duoc.dairys.pedidos.DTO.ProdInvDTO;

@Component
public class ValidarStock {

    @Autowired
    private RestTemplate restTemplate;

    public boolean verificarStock (Long idInventario, Long idProducto){
        Optional<ProdInvDTO> producto = Optional.of(restTemplate.getForObject("http://localhost:8081/api/ecomarket/v1/productos/inventario" + idInventario + "/producto/" + idProducto + "/stock", ProdInvDTO.class));
        ProdInvDTO product = producto.get();
        int i = product.getStockActual();

        if(idProducto == 0){
            return false;
        }
        return true;
    }
    
}
