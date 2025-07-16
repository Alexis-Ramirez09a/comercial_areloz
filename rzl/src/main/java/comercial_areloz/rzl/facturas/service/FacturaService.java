package comercial_areloz.rzl.facturas.service;

import java.math.BigDecimal;

import java.util.Optional;

import org.springframework.stereotype.Service;

import comercial_areloz.rzl.clientes.model.Cliente;
import comercial_areloz.rzl.clientes.repository.ClienteRepositorio;
import comercial_areloz.rzl.facturas.model.DetalleFactura;
import comercial_areloz.rzl.facturas.model.Factura;
import comercial_areloz.rzl.facturas.repository.FacturaRepository;

@Service
public class FacturaService {

    private final FacturaRepository facturaRepo;
    private final ClienteRepositorio clienteRepo;

    public FacturaService(FacturaRepository facturaRepo, ClienteRepositorio clienteRepo) {
        this.facturaRepo = facturaRepo;
        this.clienteRepo = clienteRepo;
    }

    public Factura guardarFacturaConDetalles(Factura factura, Long clienteId) throws IllegalArgumentException {
        Optional<Cliente> clienteOpt = clienteRepo.findById(clienteId);
        if (clienteOpt.isEmpty()) {
            throw new IllegalArgumentException("Cliente no encontrado");
        }
        factura.setCliente(clienteOpt.get());

        if (factura.getDetalles() == null || factura.getDetalles().isEmpty()) {
            throw new IllegalArgumentException("Debe agregar al menos un producto");
        }

        BigDecimal subtotal = BigDecimal.ZERO;
        BigDecimal descuentoTotal = BigDecimal.ZERO;

        for (DetalleFactura d : factura.getDetalles()) {
            BigDecimal cantidad = new BigDecimal(d.getCantidad());
            BigDecimal importe = d.getPrecioUnitario().multiply(cantidad);
            d.setImporte(importe);

            BigDecimal descuento = d.getDescuento() != null ? d.getDescuento() : BigDecimal.ZERO;
            descuentoTotal = descuentoTotal.add(descuento);

            subtotal = subtotal.add(importe);
            d.setFactura(factura);
        }

        BigDecimal subtotalConDescuento = subtotal.subtract(descuentoTotal);
        BigDecimal iva = subtotalConDescuento.multiply(new BigDecimal("0.12"));
        BigDecimal total = subtotalConDescuento.add(iva);

        factura.setSubtotal(subtotal);
        factura.setDescuento(descuentoTotal);
        factura.setIva(iva);
        factura.setTotal(total);

        return facturaRepo.save(factura);
    }
}
