package comercial_areloz.rzl.clientes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import comercial_areloz.rzl.clientes.model.Cliente;
import comercial_areloz.rzl.clientes.repository.ClienteRepositorio;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Override
    public List<Cliente> listarTodos() {
        return clienteRepositorio.findAll();
    }

    @Override
    public Cliente guardar(Cliente cliente) {
        return clienteRepositorio.save(cliente);
    }

    @Override
    public Cliente obtenerPorId(Long id) {
        return clienteRepositorio.findById(id).orElse(null);
    }

    @Override
    public void eliminar(Long id) {
        clienteRepositorio.deleteById(id);
    }

    @Override
    public List<Cliente> buscarPorNombreOCedula(String texto) {
        return clienteRepositorio.findByCedulaContainingIgnoreCaseOrNombreContainingIgnoreCase(texto, texto);
    }
}
