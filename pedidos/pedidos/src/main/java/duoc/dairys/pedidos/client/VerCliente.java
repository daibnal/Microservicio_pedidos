package duoc.dairys.pedidos.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class VerCliente {
    @Autowired
    private RestTemplate restTemplate;

    public boolean existeCliente(Long idCliente) {
        Boolean respuesta = restTemplate.getForObject("http://localhost:8086/api/v1/sesiones/validacion-cliente/exe/" + idCliente, boolean.class);
        return Boolean.TRUE.equals(respuesta);
        
    }
}
