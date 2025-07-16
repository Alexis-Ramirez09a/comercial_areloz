package comercial_areloz.rzl.proveedores.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import comercial_areloz.rzl.proveedores.model.Proveedores;
import comercial_areloz.rzl.proveedores.repository.ProveedoresRepository;

@Service
public class ProveedoresService {

    private final ProveedoresRepository repo;

    public ProveedoresService(ProveedoresRepository repo) {
        this.repo = repo;
    }

    public List<Proveedores> listar() {
        return repo.findAll();
    }

    public Optional<Proveedores> buscarPorId(Integer id) {
        return repo.findById(id);
    }

    public Proveedores guardar(Proveedores p) {
        return repo.save(p);
    }

    public boolean eliminar(Integer id) {
        if (id != null && repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        }
        return false;
    }

    /** Comprueba si existe algún otro proveedor con el mismo email */
    public boolean existeEmailDuplicado(Proveedores p) {
        if (p.getEmail() == null || p.getEmail().isBlank()) {
            return false;
        }
        List<Proveedores> lista = repo.findByEmailIgnoreCase(p.getEmail());
        return lista.stream()
                .anyMatch(ex -> !ex.getId().equals(p.getId()));
    }

    /** Comprueba si existe cédula duplicada (único en BD) */
    public boolean existeCedula(String cedula) {
        return repo.findByCedula(cedula).isPresent();
    }

    public Optional<Proveedores> buscarPorCedula(String cedula) {
        return repo.findByCedula(cedula);
    }
}
