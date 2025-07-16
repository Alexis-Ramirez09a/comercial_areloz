package comercial_areloz.rzl.proveedores.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import comercial_areloz.rzl.proveedores.model.Proveedores;

public interface ProveedoresRepository extends JpaRepository<Proveedores, Integer> {
    // Devuelve todos los proveedores cuyo nombre (ignorando mayúsculas) coincida
    List<Proveedores> findByNombreIgnoreCase(String nombre);

    // Devuelve todos los proveedores cuyo email (ignorando mayúsculas) coincida
    List<Proveedores> findByEmailIgnoreCase(String email);

    // Para cédula, sigue siendo único
    Optional<Proveedores> findByCedula(String cedula);
}
