package comercial_areloz.rzl.security.repository;

import comercial_areloz.rzl.security.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {
    // Método para buscar un rol por su nombre (p. ej. ROLE_ADMIN)
    Optional<Rol> findByNombre(String nombre);
}
