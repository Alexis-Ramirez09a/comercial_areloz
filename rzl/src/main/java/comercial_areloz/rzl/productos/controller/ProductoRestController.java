package comercial_areloz.rzl.productos.controller;

import comercial_areloz.rzl.productos.model.Producto;
import comercial_areloz.rzl.productos.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoRestController {

    private final ProductoService productoService;

    @Autowired
    public ProductoRestController(ProductoService productoService) {
        this.productoService = productoService;
    }

    // Endpoint para buscar productos por nombre o descripción
    @GetMapping("/search")
    public List<Producto> buscarProductos(@RequestParam("q") String query) {
        return productoService.buscarPorNombreODescripcion(query);
    }
}
