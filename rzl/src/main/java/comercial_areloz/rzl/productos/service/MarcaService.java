package comercial_areloz.rzl.productos.service;

import comercial_areloz.rzl.productos.model.Marca;
import comercial_areloz.rzl.productos.model.Producto;
import comercial_areloz.rzl.productos.repository.MarcaRepository;
import comercial_areloz.rzl.productos.repository.ProductoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MarcaService {

    private final MarcaRepository marcaRepo;
    private final ProductoRepository productoRepo;

    @Autowired
    public MarcaService(MarcaRepository marcaRepo,
            ProductoRepository productoRepo) {
        this.marcaRepo = marcaRepo;
        this.productoRepo = productoRepo;
    }

    public List<Marca> listarTodas() {
        return marcaRepo.findAll();
    }

    public Optional<Marca> buscarPorId(Long id) {
        return marcaRepo.findById(id);
    }

    public Marca guardar(Marca marca) {
        return marcaRepo.save(marca);
    }

    public void eliminar(Long id) {
        List<Producto> usados = productoRepo.findByMarcaId(id);
        if (!usados.isEmpty()) {
            // Construimos mensaje con nombres de productos
            String lista = usados.stream()
                    .map(Producto::getNombre)
                    .collect(Collectors.joining(", "));
            throw new IllegalStateException(
                    "No se puede eliminar: esta marca está siendo usada por los productos: " + lista);
        }
        marcaRepo.deleteById(id);
    }
}
