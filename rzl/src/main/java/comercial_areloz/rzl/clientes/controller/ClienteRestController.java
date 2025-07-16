package comercial_areloz.rzl.clientes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import comercial_areloz.rzl.clientes.model.Cliente;
import comercial_areloz.rzl.clientes.service.ClienteService;

@RestController
@RequestMapping("/api/clientes")
public class ClienteRestController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/search")
    public List<Cliente> buscarClientes(@RequestParam("q") String query) {
        return clienteService.buscarPorNombreOCedula(query);
    }
}
