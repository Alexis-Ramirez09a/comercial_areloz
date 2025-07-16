package comercial_areloz.rzl.security.repository;

import comercial_areloz.rzl.security.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Método para buscar un usuario por su username
    Optional<Usuario> findByUsername(String username);
}
