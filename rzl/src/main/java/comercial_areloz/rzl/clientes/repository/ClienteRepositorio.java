package comercial_areloz.rzl.clientes.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import comercial_areloz.rzl.clientes.model.Cliente;

public interface ClienteRepositorio extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByCedula(String cedula);

    List<Cliente> findByCedulaContainingIgnoreCaseOrNombreContainingIgnoreCase(String cedula, String nombre);
}
