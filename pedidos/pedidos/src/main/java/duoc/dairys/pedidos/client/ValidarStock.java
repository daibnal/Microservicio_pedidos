package duoc.dairys.pedidos.client;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import duoc.dairys.pedidos.DTO.ProdInvDTO;

@Component
public class ValidarStock {

    @Autowired
    private RestTemplate restTemplate;

    public boolean verificarStock(Long idInventario, Long idProducto) {

        if (idInventario == null || idProducto == null || idProducto == 0) {
            return false;
        }

        String url = "http://localhost:8081/api/ecomarket/v1/productos/inventario/" + idInventario + "/producto/" + idProducto + "/stock";

        ProdInvDTO producto = restTemplate.getForObject(url, ProdInvDTO.class);

        if (producto == null) {
            return false;
        }
        return producto.getStockActual() > 0;
    }
    
}
