package co.edu.uniquindio.SOLID.Service;

import co.edu.uniquindio.SOLID.Model.DTO.EmpleadoDTO;
import co.edu.uniquindio.SOLID.Model.Empleado;
import co.edu.uniquindio.SOLID.Model.Minimercado;
import co.edu.uniquindio.SOLID.utils.Mappers.EmpleadoMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


public class EmpleadoService {

    private final Minimercado minimercado;

    public EmpleadoService(Minimercado minimercado) {
        this.minimercado = minimercado;
    }


    public boolean crearEmpleado(EmpleadoDTO dto) {
        if (dto == null || dto.getId() == null || dto.getNombre() == null || dto.getRol() == null)
            throw new IllegalArgumentException("Datos del empleado incompletos");

        if (buscarEmpleadoPorId(dto.getId()).isPresent())
            throw new IllegalArgumentException("Ya existe un empleado con ese ID");

        minimercado.getEmpleados().add(EmpleadoMapper.toEntity(dto));
        return true;
    }

    public boolean actualizarEmpleado(EmpleadoDTO dto) {
        Empleado empleado = buscarEmpleadoPorId(dto.getId())
                .orElseThrow(() -> new NoSuchElementException("Empleado no encontrado"));

        EmpleadoMapper.updateEntityFromDTO(empleado, dto);
        return true;
    }

    public boolean eliminarEmpleado(String id) {
        Empleado empleado = buscarEmpleadoPorId(id)
                .orElseThrow(() -> new NoSuchElementException("Empleado no encontrado"));

        minimercado.getEmpleados().remove(empleado);
        return true;
    }

    public boolean activarEmpleado(String id) {
        Empleado empleado = buscarEmpleadoPorId(id)
                .orElseThrow(() -> new NoSuchElementException("Empleado no encontrado"));

        empleado.activar();
        return true;
    }

    public boolean inactivarEmpleado(String id) {
        Empleado empleado = buscarEmpleadoPorId(id)
                .orElseThrow(() -> new NoSuchElementException("Empleado no encontrado"));

        empleado.inactivar();
        return true;
    }


    public List<EmpleadoDTO> listarEmpleados() {
        List<EmpleadoDTO> lista = new ArrayList<>();
        for (Empleado e : minimercado.getEmpleados()) {
            lista.add(EmpleadoMapper.toDTO(e));
        }
        return lista;
    }

    private Optional<Empleado> buscarEmpleadoPorId(String id) {
        return minimercado.getEmpleados().stream()
                .filter(e -> e.getId().equalsIgnoreCase(id))
                .findFirst();
    }
}
