package comercial_areloz.rzl.facturas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import comercial_areloz.rzl.facturas.model.Factura;

public interface FacturaRepository extends JpaRepository<Factura, Long> {
    // Aquí puedes agregar consultas personalizadas si las necesitas
}
