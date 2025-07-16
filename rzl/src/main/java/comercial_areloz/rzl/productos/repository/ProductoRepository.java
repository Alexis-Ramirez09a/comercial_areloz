package comercial_areloz.rzl.productos.repository;

import comercial_areloz.rzl.productos.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    List<Producto> findByCategoriaId(Long categoriaId);

    List<Producto> findByMarcaId(Long marcaId);

    // Búsqueda por nombre o descripción (ignorando mayúsculas/minúsculas)
    List<Producto> findByNombreContainingIgnoreCaseOrDescripcionContainingIgnoreCase(String nombre, String descripcion);
}
