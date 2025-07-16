package comercial_areloz.rzl.productos.controller;

import comercial_areloz.rzl.productos.model.Marca;
import comercial_areloz.rzl.productos.service.MarcaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/marcas")
public class MarcaController {

    private final MarcaService marcaService;

    @Autowired
    public MarcaController(MarcaService marcaService) {
        this.marcaService = marcaService;
    }

    // Listar todas
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("marcas", marcaService.listarTodas());
        return "productos/marcas/list";
    }

    // Formulario de creación
    @GetMapping("/nueva")
    public String nuevoFormulario(Model model) {
        model.addAttribute("marca", new Marca());
        return "productos/marcas/form";
    }

    // Guardar (creación y edición)
    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute Marca marca,
            BindingResult result) {
        if (result.hasErrors()) {
            return "productos/marcas/form";
        }
        marcaService.guardar(marca);
        return "redirect:/productos/nuevo";
    }

    // Formulario de edición
    @GetMapping("/editar/{id}")
    public String editarFormulario(@PathVariable Long id, Model model, RedirectAttributes redirectAttrs) {
        var marcaOpt = marcaService.buscarPorId(id);
        if (marcaOpt.isPresent()) {
            model.addAttribute("marca", marcaOpt.get());
            return "productos/marcas/form";
        } else {
            redirectAttrs.addFlashAttribute("msgError", "Marca no encontrada");
            return "redirect:/marcas";
        }
    }

    // Eliminar
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes redir) {
        try {
            marcaService.eliminar(id);
            redir.addFlashAttribute("msgExito", "Marca eliminada correctamente.");
        } catch (IllegalStateException ex) {
            redir.addFlashAttribute("msgError", ex.getMessage());
        }
        return "redirect:/marcas";
    }
}
