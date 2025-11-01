package co.edu.uniquindio.SOLID.utils.Mappers;


import co.edu.uniquindio.SOLID.Model.Empleado;
import co.edu.uniquindio.SOLID.Model.DTO.EmpleadoDTO;

public class EmpleadoMapper {

    // Convierte de Entidad → DTO
    public static EmpleadoDTO toDTO(Empleado empleado) {
        if (empleado == null) return null;
        return new EmpleadoDTO(
                empleado.getId(),
                empleado.getNombre(),
                empleado.getRol(),
                empleado.isActivo()
        );
    }

    // Convierte de DTO → Entidad
    public static Empleado toEntity(EmpleadoDTO dto) {
        if (dto == null) return null;
        return new Empleado(
                dto.getId(),
                dto.getNombre(),
                dto.getRol()
        );
    }

    // Actualiza una entidad existente con los valores del DTO
    public static void updateEntityFromDTO(Empleado empleado, EmpleadoDTO dto) {
        if (empleado == null || dto == null) return;

        empleado.setNombre(dto.getNombre());
        empleado.setRol(dto.getRol());
        if (dto.isActivo()) {
            empleado.activar();
        } else {
            empleado.inactivar();
        }
    }
}
