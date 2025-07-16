package comercial_areloz.rzl.facturas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import comercial_areloz.rzl.facturas.model.DetalleFactura;

public interface DetalleFacturaRepository extends JpaRepository<DetalleFactura, Long> {
    // Consultas personalizadas para detalles si las necesitas
}
