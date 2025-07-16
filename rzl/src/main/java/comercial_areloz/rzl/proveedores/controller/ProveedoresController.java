package comercial_areloz.rzl.proveedores.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import comercial_areloz.rzl.proveedores.model.Proveedores;
import comercial_areloz.rzl.proveedores.service.ProveedoresService;

@Controller
@RequestMapping("/proveedores")
public class ProveedoresController {

    private final ProveedoresService service;

    public ProveedoresController(ProveedoresService service) {
        this.service = service;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("proveedores", service.listar());
        return "proveedores/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("proveedor", new Proveedores());
        return "proveedores/formulario";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model) {
        Proveedores p = service.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("ID inválido:" + id));
        model.addAttribute("proveedor", p);
        return "proveedores/formulario";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("proveedor") Proveedores proveedor,
            BindingResult br) {

        // Validar duplicado de cédula SOLO al crear
        if (proveedor.getId() == null && service.existeCedula(proveedor.getCedula())) {
            br.rejectValue("cedula", "error.proveedor", "La cédula ya existe");
        }

        // Validar email duplicado (si aplica)
        if (service.existeEmailDuplicado(proveedor)) {
            br.rejectValue("email", "error.proveedor", "El email ya existe");
        }

        // Si hubo errores, regresar al formulario
        if (br.hasErrors()) {
            return "proveedores/formulario";
        }

        service.guardar(proveedor);
        return "redirect:/proveedores";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("id") Integer id) {
        service.eliminar(id);
        return "redirect:/proveedores";
    }
}
