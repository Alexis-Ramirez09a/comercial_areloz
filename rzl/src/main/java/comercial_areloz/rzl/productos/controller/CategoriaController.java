package comercial_areloz.rzl.productos.controller;

import comercial_areloz.rzl.productos.model.Categoria;
import comercial_areloz.rzl.productos.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    @Autowired
    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    // Listar todas
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("categorias", categoriaService.listarTodas());
        return "productos/categorias/list";
    }

    // Formulario de creación
    @GetMapping("/nueva")
    public String nuevoFormulario(Model model) {
        model.addAttribute("categoria", new Categoria());
        return "productos/categorias/form";
    }

    // Guardar (creación y edición)
    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute Categoria categoria,
            BindingResult result) {
        if (result.hasErrors()) {
            return "productos/categorias/form";
        }
        categoriaService.guardar(categoria);
        return "redirect:/productos/nuevo";
    }

    // Formulario de edición
    @GetMapping("/editar/{id}")
    public String editarFormulario(@PathVariable Long id, Model model, RedirectAttributes redirectAttrs) {
        var catOpt = categoriaService.buscarPorId(id);
        if (catOpt.isPresent()) {
            model.addAttribute("categoria", catOpt.get());
            return "productos/categorias/form";
        } else {
            redirectAttrs.addFlashAttribute("msgError", "Categoría no encontrada");
            return "redirect:/categorias";
        }
    }

    // Eliminar
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes redir) {
        try {
            categoriaService.eliminar(id);
            redir.addFlashAttribute("msgExito", "Categoría eliminada correctamente.");
        } catch (IllegalStateException ex) {
            redir.addFlashAttribute("msgError", ex.getMessage());
        }
        return "redirect:/categorias";
    }
}
