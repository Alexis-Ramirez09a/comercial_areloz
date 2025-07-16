package comercial_areloz.rzl.empleados.service;

import java.util.List;

import comercial_areloz.rzl.empleados.model.Empleado;

public interface EmpleadoServicio {
    List<Empleado> listarTodos();

    void guardar(Empleado empleado);

    Empleado obtenerPorId(Long id);

    void eliminar(Long id);
}
