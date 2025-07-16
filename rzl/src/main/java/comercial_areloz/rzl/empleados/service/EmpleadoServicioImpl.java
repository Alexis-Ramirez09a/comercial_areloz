package comercial_areloz.rzl.empleados.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import comercial_areloz.rzl.empleados.model.Empleado;
import comercial_areloz.rzl.empleados.repository.EmpleadoRepositorio;

@Service
public class EmpleadoServicioImpl implements EmpleadoServicio {

    @Autowired
    private EmpleadoRepositorio empleadoRepositorio;

    @Override
    public List<Empleado> listarTodos() {
        return empleadoRepositorio.findAll();
    }

    @Override
    public void guardar(Empleado empleado) {
        empleadoRepositorio.save(empleado);
    }

    @Override
    public Empleado obtenerPorId(Long id) {
        return empleadoRepositorio.findById(id).orElse(null);
    }

    @Override
    public void eliminar(Long id) {
        empleadoRepositorio.deleteById(id);
    }
}
