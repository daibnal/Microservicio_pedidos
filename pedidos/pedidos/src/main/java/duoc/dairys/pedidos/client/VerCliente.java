package duoc.dairys.pedidos.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import duoc.dairys.pedidos.DTO.ClienteDTO;

@Component
public class VerCliente {
    @Autowired
    private RestTemplate restTemplate;

    public boolean existeCliente(Long idCliente) {
        String url = "http://localhost:8084/api/clientes/" + idCliente;

        try {ClienteDTO cliente = restTemplate.getForObject(url, ClienteDTO.class);
            return cliente != null && "ACTIVO".equalsIgnoreCase(cliente.getEstado());
        } catch (Exception e) {
            return false;
        }
    }
}
