package comercial_areloz.rzl.empleados.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import comercial_areloz.rzl.empleados.model.Empleado;
import comercial_areloz.rzl.empleados.service.EmpleadoServicio;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/empleados")
public class EmpleadoControlador {

    @Autowired
    private EmpleadoServicio empleadoServicio;

    @GetMapping
    public String listarEmpleados(Model modelo) {
        modelo.addAttribute("empleados", empleadoServicio.listarTodos());
        return "empleados/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioRegistro(Model modelo) {
        modelo.addAttribute("empleado", new Empleado());
        return "empleados/formulario";
    }

    @PostMapping("/guardar")
    public String guardarEmpleado(@Valid @ModelAttribute("empleado") Empleado empleado, BindingResult result) {
        if (result.hasErrors()) {
            System.out.println("❌ Errores de validación:");
            result.getAllErrors().forEach(e -> System.out.println(e.toString()));
            return "empleados/formulario";
        }

        // Solo si es un nuevo empleado (ID vacío), se asigna la fecha de registro
        if (empleado.getId() == null) {
            empleado.setFechaRegistro(new Date());
        }

        empleadoServicio.guardar(empleado);
        System.out.println("✅ Empleado guardado correctamente: " + empleado);
        return "redirect:/empleados";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicion(@PathVariable Long id, Model modelo) {
        Empleado empleado = empleadoServicio.obtenerPorId(id);
        if (empleado == null) {
            return "redirect:/empleados";
        }
        modelo.addAttribute("empleado", empleado);
        return "empleados/formulario";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarEmpleado(@PathVariable Long id) {
        empleadoServicio.eliminar(id);
        return "redirect:/empleados";
    }
}
