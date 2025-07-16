package comercial_areloz.rzl.clientes.service;

import java.util.List;

import comercial_areloz.rzl.clientes.model.Cliente;

public interface ClienteService {

    List<Cliente> listarTodos();

    Cliente guardar(Cliente cliente);

    Cliente obtenerPorId(Long id);

    void eliminar(Long id);

    List<Cliente> buscarPorNombreOCedula(String texto);

}
