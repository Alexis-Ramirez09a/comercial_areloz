package comercial_areloz.rzl.proveedores.ViewController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import comercial_areloz.rzl.proveedores.model.Proveedores;
import comercial_areloz.rzl.proveedores.service.ProveedoresService;

@Controller
@RequestMapping("/proveedor")
public class ProveedoresViewController {

    private final ProveedoresService service;

    public ProveedoresViewController(ProveedoresService service) {
        this.service = service;
    }

    // GET /proveedor → Lista todos los proveedores
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("proveedores", service.listar());
        return "proveedores/lista";
    }

    // GET /proveedor/nuevo → Formulario en blanco para crear
    @GetMapping("/nuevo")
    public String nuevoForm(Model model) {
        model.addAttribute("proveedor", new Proveedores());
        // Inicializamos las banderas de duplicado en false
        model.addAttribute("duplicadoCedula", false);
        model.addAttribute("duplicadoEmail", false);
        return "proveedores/formulario";
    }

    // POST /proveedor/guardar → Procesa creación
    @PostMapping("/guardar")
    public String guardar(
            @Valid @ModelAttribute("proveedor") Proveedores proveedor,
            BindingResult br,
            Model model) {
        // Validar duplicados
        boolean dupCedula = service.existeCedula(proveedor.getCedula());
        boolean dupEmail = service.existeEmailDuplicado(proveedor);

        // Añadimos las banderas al modelo para el formulario
        model.addAttribute("duplicadoCedula", dupCedula);
        model.addAttribute("duplicadoEmail", dupEmail);

        // Si hay errores de validación o duplicados, devolvemos el formulario
        if (br.hasErrors() || dupCedula || dupEmail) {
            return "proveedores/formulario";
        }

        // Guardar y redirigir
        service.guardar(proveedor);
        return "redirect:/proveedor";
    }

    // GET /proveedor/editar/{id} → Formulario con datos para editar
    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable Integer id, Model model) {
        Proveedores p = service.buscarPorId(id).orElse(new Proveedores());
        model.addAttribute("proveedor", p);
        // inicializar flags para el formulario
        model.addAttribute("duplicadoCedula", false);
        model.addAttribute("duplicadoEmail", false);
        return "proveedores/formulario";
    }

    // POST /proveedor/editar/{id} → Procesa edición
    @PostMapping("/editar/{id}")
    public String editar(
            @PathVariable Integer id,
            @Valid @ModelAttribute("proveedor") Proveedores proveedor,
            BindingResult br,
            Model model) {
        // Asignar el id a la entidad antes de validar
        proveedor.setId(id);

        // Validar duplicados, ignorando al propio registro
        boolean dupCedula = service.buscarPorCedula(proveedor.getCedula())
                .filter(ex -> !ex.getId().equals(id))
                .isPresent();
        boolean dupEmail = service.existeEmailDuplicado(proveedor);

        model.addAttribute("duplicadoCedula", dupCedula);

        model.addAttribute("duplicadoEmail", dupEmail);

        if (br.hasErrors() || dupCedula || dupEmail) {
            return "proveedores/formulario";
        }

        service.guardar(proveedor);
        return "redirect:/proveedor";
    }

    // GET /proveedor/eliminar/{id} → Elimina y redirige
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return "redirect:/proveedor";
    }

}
