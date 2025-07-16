package comercial_areloz.rzl.productos.controller;

import comercial_areloz.rzl.productos.model.Producto;
import comercial_areloz.rzl.productos.service.CategoriaService;
import comercial_areloz.rzl.productos.service.MarcaService;
import comercial_areloz.rzl.productos.service.ProductoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    private final ProductoService productoService;
    private final CategoriaService categoriaService;
    private final MarcaService marcaService;

    @Autowired
    public ProductoController(ProductoService productoService,
            CategoriaService categoriaService,
            MarcaService marcaService) {
        this.productoService = productoService;
        this.categoriaService = categoriaService;
        this.marcaService = marcaService;
    }

    // Listar productos
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("productos", productoService.listarTodos());
        return "productos/list";
    }

    // Formulario nuevo producto
    @GetMapping("/nuevo")
    public String nuevoFormulario(Model model) {
        model.addAttribute("producto", new Producto());
        model.addAttribute("productos", productoService.listarTodos()); // corregido aquí
        model.addAttribute("categorias", categoriaService.listarTodas());
        model.addAttribute("marcas", marcaService.listarTodas());
        return "productos/form";
    }

    // Guardar producto (nuevo o edición)
    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute Producto producto,
            BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categorias", categoriaService.listarTodas());
            model.addAttribute("marcas", marcaService.listarTodas());
            return "productos/form";
        }
        productoService.guardar(producto);
        return "redirect:/productos";
    }

    // Formulario edición
    @GetMapping("/editar/{id}")
    public String editarFormulario(@PathVariable Long id, Model model) {
        Producto producto = productoService.buscarPorId(id).orElse(null);

        if (producto == null) {
            return "redirect:/productos?error=notfound";
        }

        model.addAttribute("producto", producto);
        model.addAttribute("categorias", categoriaService.listarTodas());
        model.addAttribute("marcas", marcaService.listarTodas());

        return "productos/form";
    }

    // Eliminar producto
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        productoService.eliminar(id);
        return "redirect:/productos";
    }

    // Gestión
    @GetMapping("/gestion")
    public String gestion() {
        return "productos/gestion";
    }
}
