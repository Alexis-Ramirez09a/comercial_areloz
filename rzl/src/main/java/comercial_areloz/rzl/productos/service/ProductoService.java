package comercial_areloz.rzl.productos.service;

import comercial_areloz.rzl.productos.model.Producto;
import comercial_areloz.rzl.productos.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    private final ProductoRepository productoRepo;

    @Autowired
    public ProductoService(ProductoRepository productoRepo) {
        this.productoRepo = productoRepo;
    }

    // Listar todos los productos
    public List<Producto> listarTodos() {
        return productoRepo.findAll();
    }

    // Buscar producto por ID
    public Optional<Producto> buscarPorId(Long id) {
        return productoRepo.findById(id);
    }

    // Guardar o actualizar producto
    public Producto guardar(Producto producto) {
        return productoRepo.save(producto);
    }

    // Eliminar producto por ID
    public void eliminar(Long id) {
        productoRepo.deleteById(id);
    }

    // Buscar por nombre o descripción (usado en búsqueda dinámica)
    public List<Producto> buscarPorNombreODescripcion(String texto) {
        return productoRepo.findByNombreContainingIgnoreCaseOrDescripcionContainingIgnoreCase(texto, texto);
    }
}
