package co.edu.uniquindio.SOLID.utils.Mappers;

import co.edu.uniquindio.SOLID.Model.DTO.ProveedorDTO;
import co.edu.uniquindio.SOLID.Model.Proveedor;


public class ProveedorMapper {

    public static Proveedor toEntity(ProveedorDTO dto) {
        return new Proveedor(
                dto.getNit(),
                dto.getNombre(),
                dto.getContacto(),
                dto.getEmail(),
                dto.getTelefono()
        );
    }

    public static ProveedorDTO toDTO(Proveedor proveedor) {
        return new ProveedorDTO(
                proveedor.getNit(),
                proveedor.getNombre(),
                proveedor.getContacto(),
                proveedor.getEmail(),
                proveedor.getTelefono(),
                proveedor.isActivo()
        );
    }

    public static void updateEntityFromDTO(Proveedor proveedor, ProveedorDTO dto) {
        if (dto.getNombre() != null) proveedor.setNombre(dto.getNombre());
        if (dto.getContacto() != null) proveedor.setContacto(dto.getContacto());
        if (dto.getEmail() != null) proveedor.setEmail(dto.getEmail());
        if (dto.getTelefono() != null) proveedor.setTelefono(dto.getTelefono());
        if (dto.isActivo()) {
            if (dto.isActivo()) proveedor.activar();
            else proveedor.inactivar();
        }
    }

}
