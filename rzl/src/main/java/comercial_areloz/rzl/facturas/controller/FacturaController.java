package comercial_areloz.rzl.facturas.controller;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import comercial_areloz.rzl.clientes.model.Cliente;
import comercial_areloz.rzl.clientes.repository.ClienteRepositorio;
import comercial_areloz.rzl.facturas.model.DetalleFactura;
import comercial_areloz.rzl.facturas.model.Factura;
import comercial_areloz.rzl.facturas.repository.FacturaRepository;
import comercial_areloz.rzl.productos.model.Producto;
import comercial_areloz.rzl.productos.repository.ProductoRepository;

@Controller
@RequestMapping("/factura")
public class FacturaController {

    @Autowired
    private FacturaRepository facturaRepo;

    @Autowired
    private ClienteRepositorio clienteRepo;

    @Autowired
    private ProductoRepository productoRepo;

    @GetMapping("/tipo")
    public String tipo() {
        return "factura/tipo";
    }

    @GetMapping("/con-datos")
    public String conDatos(Model model) {
        // Crear nueva factura con fecha actual
        Factura factura = new Factura();
        factura.setFecha(LocalDateTime.now());
        // Cargar cliente “Consumidor Final” de la base (cédula = "CF")
        Cliente cf = clienteRepo.findByCedula("9999999999")
                .orElseThrow(() -> new IllegalStateException(
                        "Debe existir un cliente con cédula '9999999999' para Consumidor Final"));
        factura.setCliente(cf);

        model.addAttribute("factura", factura);
        return "factura/con-datos";
    }

    @GetMapping("/lista")
    public String lista(Model model, @ModelAttribute("msg") String msg) {
        List<Factura> facturas = facturaRepo.findAll();
        model.addAttribute("facturas", facturas);
        model.addAttribute("mensaje", msg);
        return "factura/lista";
    }

    @PostMapping
    public String guardar(
            @Valid @ModelAttribute("factura") Factura factura,
            BindingResult br,
            Model model,
            RedirectAttributes redirectAttrs) {

        // Asociar cliente real por cédula
        if (factura.getCliente() != null && factura.getCliente().getCedula() != null) {
            factura.setCliente(
                    clienteRepo.findByCedula(factura.getCliente().getCedula()).orElse(null));
        }

        if (factura.getDetalles() == null || factura.getDetalles().isEmpty()) {
            br.rejectValue("detalles", "error.factura", "Debe agregar al menos un producto");
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

        facturaRepo.save(factura);

        redirectAttrs.addFlashAttribute("msg", "Factura guardada correctamente");
        return "redirect:/factura/lista";
    }

    @PostMapping("/eliminar")
    public String eliminar(@ModelAttribute("id") Long id, RedirectAttributes redirectAttrs) {
        facturaRepo.deleteById(id);
        redirectAttrs.addFlashAttribute("msg", "Factura eliminada");
        return "redirect:/factura/lista";
    }

    @GetMapping("/ver/{id}")
    public void verFacturaPdf(@PathVariable Long id, HttpServletResponse response) throws Exception {
        Factura factura = facturaRepo.findById(id).orElse(null);
        if (factura == null) {
            response.sendRedirect("/factura/lista");
            return;
        }

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=factura_" + id + ".pdf");

        try (OutputStream out = response.getOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, out);
            document.open();

            document.add(new Paragraph("Factura #" + factura.getId()));
            document.add(new Paragraph("Cliente: " + factura.getCliente().getNombre()));
            document.add(new Paragraph("Fecha: " + factura.getFecha().toLocalDate().toString()));
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.addCell("Producto");
            table.addCell("Cantidad");
            table.addCell("Precio Unitario");
            table.addCell("Subtotal");

            for (DetalleFactura item : factura.getDetalles()) {
                Producto producto = productoRepo.findById(item.getProductoId()).orElse(null);
                if (producto != null) {
                    item.setNombre(producto.getNombre());
                    item.setDescripcion(producto.getDescripcion());
                }
                // Uso del campo nombre de DetalleFactura
                table.addCell(item.getNombre());
                table.addCell(String.valueOf(item.getCantidad()));
                table.addCell("$" + item.getPrecioUnitario());
                table.addCell("$" + item.getImporte());
            }

            document.add(table);
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Total a pagar: $" + factura.getTotal()));

            document.close();
        }
    }

}
