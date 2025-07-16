package comercial_areloz.rzl.productos.service;

import comercial_areloz.rzl.productos.model.Categoria;
import comercial_areloz.rzl.productos.model.Producto;
import comercial_areloz.rzl.productos.repository.CategoriaRepository;
import comercial_areloz.rzl.productos.repository.ProductoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepo;
    private final ProductoRepository productoRepo;

    @Autowired
    public CategoriaService(CategoriaRepository categoriaRepo,
            ProductoRepository productoREpo) {
        this.categoriaRepo = categoriaRepo;
        this.productoRepo = productoREpo;
    }

    public List<Categoria> listarTodas() {
        return categoriaRepo.findAll();
    }

    public Optional<Categoria> buscarPorId(Long id) {
        return categoriaRepo.findById(id);
    }

    public Categoria guardar(Categoria categoria) {
        return categoriaRepo.save(categoria);
    }

    public void eliminar(Long id) {
        List<Producto> usados = productoRepo.findByCategoriaId(id);
        if (!usados.isEmpty()) {
            // Construimos mensaje con nombres de productos
            String lista = usados.stream()
                    .map(Producto::getNombre)
                    .collect(Collectors.joining(", "));
            throw new IllegalStateException(
                    "No se puede eliminar: esta categoría está siendo usada por los productos: " + lista);
        }
        categoriaRepo.deleteById(id);
    }
}
