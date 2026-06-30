package duoc.dairys.pedidos.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import duoc.dairys.pedidos.DTO.VentaDTO;
@Component
public class ConVenta {
    @Autowired
    private RestTemplate restTemplate;

    public void registrarVenta(VentaDTO ventaDTO) {
        restTemplate.postForObject("http://localhost:8086/api/ventas", ventaDTO, Object.class);
    }
}
